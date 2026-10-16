/**
 * LottoAIMain.java
 */
package kr.pe.lotto.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.nd4j.linalg.dataset.MultiDataSet;

import kr.pe.lotto.LottoCoreFunctions;
import kr.pe.lotto.LottoMain;
import kr.pe.lotto.filter.LottoFilter;
import kr.pe.lotto.filter.range.SumRangeFilter;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;
import kr.pe.util.Arr;
import kr.pe.util.Coll;
import kr.pe.util.Str;
import kr.pe.util.rnd.Rnd;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 16.
 */
public class LottoAIMain extends LottoMain {

	private LottoCoreFunctions core;

	List<LottoFilter> myFilters;

	private double[] nextProbabilityList;

	private List<NumberVO> triedVos;
	private List<NumberVO> selectVos;

	private String allFilename, filename;

	/**
	 * <pre></pre>
	 * 
	 * @param allfilename
	 * @param filename
	 */
	public LottoAIMain(String allfilename, String filename, int lowSum, int highSum) {
		super();

		NumberInfoVO lastVo = this.getLastVo();
		int round = lastVo.getRound() + 1;
		String roundStr = String.valueOf(round);
		this.allFilename = allfilename.replace("{round}", roundStr);
		this.filename = filename.replace("{round}", roundStr);

		long start = System.currentTimeMillis();
		this.logger.debug(Str.timeToString2(start));

		this.core = LottoCoreFunctions.getInstance(this.lottoList);
		this.core.addFilter(8, new SumRangeFilter("Sum_00", lowSum, highSum)); // 94%

		// =================================================================
		// [준비 단계 1] 기존 자바 시스템의 데이터 및 필터 로드
		// =================================================================
		// 1. 역대 당첨 번호 데이터 로드 (예: 1회차부터 최신 회차까지 전체 리스트)
		// 각 내부 List<Integer>는 6개의 정수([1, 5, 13, 24, 35, 42])를 담고 있어야 합니다.
		List<List<Integer>> allLottoHistory = loadAllLottoHistoryFromJavaSystem();

		// 2. 기존 자바 시스템에 구현된 수십 개의 필터 리스트 로드
		this.myFilters = this.core.getFilters();
		int numFilters = this.myFilters.size();

		this.logger.debug("📊 로딩된 역대 회차 수: " + allLottoHistory.size() + " 회차");
		this.logger.debug("⚙️ 적용할 필터 지표 개수: " + numFilters + " 개");

		// =================================================================
		// [준비 단계 2] 2차원 필터 행렬(Matrix) 생성 및 자체 StandardScaler 적용
		// =================================================================
		int totalRows = allLottoHistory.size();
		double[][] rawFilterMatrix = new double[totalRows][numFilters];

		// 모든 회차를 순회하며 각 필터의 통과(1.0) / 탈락(0.0) 여부를 행렬로 빌드
		for (int i = 0; i < totalRows; i++) {
			List<Integer> numbers = allLottoHistory.get(i);
			for (int f = 0; f < numFilters; f++) {
				rawFilterMatrix[i][f] = this.myFilters.get(f).filter(numbers) ? 1.0 : 0.0;
			}
		}

		// 파이썬의 StandardScaler와 1:1 대응하는 수치 평탄화(정규화) 수행
		double[][] normalizedFilterMatrix = applyStandardScaler(rawFilterMatrix, totalRows, numFilters);

		// =================================================================
		// [실행 단계 3] 딥러닝 인스턴스 생성 및 데이터셋 빌드
		// =================================================================
		LottoDeepLearningEngine aiEngine = new LottoDeepLearningEngine();

		// 입력 필터 개수에 맞추어 다중 입력 LSTM 신경망 모델 빌드
		aiEngine.buildMultiInputModel(numFilters);

		// 시점 오류(Data Leakage)가 차단된 순수 자바 인덱싱 기반 3차원 학습 데이터셋 생성
		MultiDataSet trainingData = aiEngine.prepareTrainingData(allLottoHistory, normalizedFilterMatrix);

		// =================================================================
		// [실행 단계 4] 자바 환경에서 자체 모델 학습(Train) 수행
		// =================================================================
		int epochs = 100; // 학습 반복 횟수 설정
		aiEngine.trainModel(trainingData, epochs);
		this.logger.debug("🎉 자바 자체 임베디드 신경망 학습이 완료되었습니다.");

		// =================================================================
		// [예측 단계 5] 다음 회차 예측을 위한 최신 5개 회차 데이터 추출 및 입력
		// =================================================================
		// 가장 최신 5개 회차의 번호 흐름 획득
		List<List<Integer>> last5Numbers = allLottoHistory.subList(totalRows - 5, totalRows);

		// 가장 최신 5개 회차의 정규화된 필터 상태 행렬 획득
		double[][] last5Filters = new double[5][numFilters];
		for (int w = 0; w < 5; w++) {
			last5Filters[w] = normalizedFilterMatrix[(totalRows - 5) + w];
		}

		// 💡 다음 회차에 출현할 1~45번 번호별 Softmax 확률 리스트(prob_list) 추출
		this.nextProbabilityList = aiEngine.predictNextProbability(last5Numbers, last5Filters);

		// =================================================================
		// [결과 출력] 1번부터 45번까지의 AI 예측 확률 검증
		// =================================================================
		this.logger.debug("\n🔮 [AI가 예측한 다음 회차 번호별 출현 가중치 확률]");
		for (int i = 0; i < 45; i++) {
			System.out.printf("번호 %2d번: 확률 %.4f%%\n", (i + 1), this.nextProbabilityList[i] * 100);
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * @param tried 파이썬처럼 최대 루프를 돌며 스캔할 난수 조합 생성 횟수 (15만 건)
	 * @param games 최종으로 뽑고 싶은 최종 대박 조합 수 (5게임)
	 */
	private void execute(int tried, int games) {
		// =================================================================
		// [추가 및 연동 단계 6] 확률 가중치를 사용한 최종 조합 샘플링 및 필터 필터링
		// =================================================================
		this.logger.debug("\n🎲 [확률 가중 기반 대량 추출 및 자바 필터 교차 검증 시작]");

		this.triedVos = new ArrayList<NumberVO>();
		this.selectVos = new ArrayList<NumberVO>();

		// 파이썬의 selected_numbers처럼 추출 대상 후보 풀을 설정 (여기서는 1~45번 전체 활용 기준)
		int[] last15 = this.getNumbersFromLast(15);
		List<Integer> selectedNumbersPool = Arr.intArrayAsList(last15);

		List<List<Integer>> finalTickets = generateLottoTickets(this.nextProbabilityList, selectedNumbersPool, games,
				tried, this.myFilters);

		// 최종 결과 출력
		this.logger.debug("\n🏆 [최종 엄격한 필터를 100% 통과한 AI 가중치 추천 번호]");
		for (int i = 0; i < finalTickets.size(); i++) {
			this.logger.debug((i + 1) + "게임: " + finalTickets.get(i));
			int[] nums = Arr.listToIntArray(finalTickets.get(i));
			Arrays.sort(nums);
			this.selectVos.add(new NumberVO(nums));
		}

		Collections.sort(this.triedVos);
		Collections.sort(this.selectVos);
		this.toSave(this.allFilename, this.triedVos, false);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		StringBuilder sb = new StringBuilder();
		String spc = "";
		for (int i = 0; i < this.selectVos.size(); i++) {
			NumberVO vo = this.selectVos.get(i);
			sb.append(spc).append(vo.toStringSum()).append(Str.TAB).append("1");
			if ((i % 3) == 2) {
				sb.append(spc);
			}
			int[] nums = vo.getNums6();
			for (int n : nums) {
				Coll.addValue(map, n);
			}
			spc = Str.LF;
		}
		// @formatter:off
		sb.append(spc)
		  .append(spc).append("03-04-17-24-26-41	115	1")
		  .append(spc).append("06-10-13-32-38-45")
		  .append(spc).append("09-12-25-27-30-32")
		  .append(spc)
		  .append(spc).append("04-08-13-22-31-44	122	1")
		  .append(spc).append("09-15-19-23-33-42")
		  .append(spc).append("08-12-17-27-36-38")
		  .append(spc);
		// @formatter:on

		List<Integer> keys = new ArrayList<Integer>(map.keySet());
		Collections.sort(keys);
		sb.append(spc).append(keys.size()).append("개 번호를 선택하였습니다.");
		sb.append(spc).append(Arr.toStringIntegerList(keys, ", "));
		sb.append(spc);
		for (int n : keys) {
			sb.append(spc).append(n).append(" = ").append(map.get(n));
		}
		sb.append(spc);
		this.toSave(this.filename, sb.toString(), false);
	}

	/**
	 * <pre>
	 * --- 기존 자바 시스템 연동을 위한 가상 메서드 (환경에 맞추어 변형) ---
	 * </pre>
	 * 
	 * @return
	 */
	private List<List<Integer>> loadAllLottoHistoryFromJavaSystem() {
		// 실제 구현하신 DB 로더나 CSV 파서 연결 영역
		// 예시용 더미 데이터 반환
		List<List<Integer>> dummyHistory = new ArrayList<>();
		for (NumberInfoVO ivo : this.lottoList) {
			dummyHistory.add(Arr.intArrayAsList(ivo.getNums6()));
		}
		return dummyHistory;
	}

	/**
	 * <pre>
	 * --- 데이터 정규화(StandardScaler) 처리 연산 스크립트 ---
	 * </pre>
	 * 
	 * @param matrix
	 * @param rows
	 * @param cols
	 * @return
	 */
	private double[][] applyStandardScaler(double[][] matrix, int rows, int cols) {
		double[][] scaled = new double[rows][cols];
		for (int j = 0; j < cols; j++) {
			double sum = 0;
			for (int i = 0; i < rows; i++)
				sum += matrix[i][j];
			double mean = sum / rows;

			double varianceSum = 0;
			for (int i = 0; i < rows; i++)
				varianceSum += Math.pow(matrix[i][j] - mean, 2);
			double stdDev = Math.sqrt(varianceSum / rows);

			for (int i = 0; i < rows; i++) {
				scaled[i][j] = (stdDev == 0) ? 0.0 : (matrix[i][j] - mean) / stdDev;
			}
		}
		return scaled;
	}

	/**
	 * 파이썬 np.random.choice(p=prob_list) 및 후처리 무결성 필터링을 완전 대체하는 자바 핵심 함수
	 */
	private List<List<Integer>> generateLottoTickets(double[] probList, List<Integer> selectedNumbers, int totalGames,
			int tried, List<LottoFilter> lottoFilters) {

		List<List<Integer>> finalTickets = new ArrayList<>();
		int attempts = 0;

		while (attempts < tried) {
			attempts++;

			// 1) AI 모델이 예측한 필터 융합 확률 가중치를 사용하여 무작위 6개 숫자 비복원 추출
			List<Integer> sampled = sampleWithProbability(selectedNumbers, probList, 6);
			Collections.sort(sampled); // 정렬 보장

			this.triedVos.add(new NumberVO(Arr.listToIntArray(sampled)));

			if (finalTickets.size() < totalGames) {
				// 2) 뽑힌 조합이 기존 자바 시스템의 모든 필터를 안전하게 만족하는지 투명하게 더블 체크
				boolean isSafe = true;
				for (LottoFilter lottoFilter : lottoFilters) {
					// 원본 객체 오염을 방지하기 위해 새로운 리스트 인스턴스로 전달
					if (!lottoFilter.filter(new ArrayList<>(sampled))) {
						isSafe = false;
						break; // 단 하나라도 필터에서 걸러지면 즉시 이 조합은 폐기(탈락)
					}
				}

				// 3) 완벽하게 안전하고, 기존에 뽑힌 번호들과 중복되지 않는 조합만 최종 안착
				if (isSafe && !finalTickets.contains(sampled)) {
					finalTickets.add(sampled);
				}
			}

			// 10만 건 단위 진행 상황 알림 (선택 사항)
			if (attempts % 10000 == 0) {
				this.logger.debug(" 진행률: {} / {} 건 파일 기록 중...", attempts, tried);
			}
		}

		this.logger.debug("📊 [샘플링 스캔 종료] 총 {}번 시도 중 필터 완벽 통과한 조합: {}개", attempts, finalTickets.size());
		return finalTickets;
	}

	/**
	 * 넘파이의 가중치 무작위 추출(np.random.choice)을 자바식 정밀 누적 확률 연산으로 재현한 헬퍼 메서드
	 */
	private List<Integer> sampleWithProbability(List<Integer> candidates, double[] probabilities, int size) {
		List<Integer> result = new ArrayList<>();
		List<Integer> pool = new ArrayList<>(candidates); // 원본 보존을 위한 카피본 풀
		Random rand = Rnd.getRandom(); // SecurityRandom

		while (result.size() < size && !pool.isEmpty()) {
			double r = rand.nextDouble();
			double cumulativeProbability = 0.0;

			// 전체 남은 번호 풀의 예측 확률 총합을 구함 (비복원 추출로 인해 유동적 변동 반영)
			double currentPoolProbSum = 0.0;
			for (int num : pool) {
				currentPoolProbSum += probabilities[num - 1];
			}

			int selectedIdx = -1;
			for (int i = 0; i < pool.size(); i++) {
				// 현재 풀 상태에서의 정규화된 가중 비율 누적 연산
				cumulativeProbability += (probabilities[pool.get(i) - 1] / currentPoolProbSum);
				if (r <= cumulativeProbability) {
					selectedIdx = i;
					break;
				}
			}

			if (selectedIdx == -1) {
				selectedIdx = pool.size() - 1;
			}

			result.add(pool.remove(selectedIdx)); // 꺼낸 숫자는 제거하여 중복 방지 (replace=False 효과)
		}
		return result;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int games = 9;
		int tried = 15 * 10000;
		int lowSum = 90, highSum = 179;
		String allfilename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_all.txt";
		String filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}.txt";

		long start = System.currentTimeMillis();
		LottoAIMain main = new LottoAIMain(allfilename, filename, lowSum, highSum);

		long end = System.currentTimeMillis();
		main.logger.debug(Str.timeToString(end - start));

		start = end;
		// 이 nextProbabilityList를 기존 가중치 랜덤 샘플링 엔진에 주입하시면 완성됩니다!
		main.execute(tried, games);

		end = System.currentTimeMillis();
		main.logger.debug(Str.timeToString(end - start));
	}
}
