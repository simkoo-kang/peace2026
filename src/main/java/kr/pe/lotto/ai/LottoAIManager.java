/**
 * LottoAIManager.java
 */
package kr.pe.lotto.ai;

/**
 * <pre></pre>
 * gbrai  2026. 10. 16.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.MultiDataSet;
import org.nd4j.linalg.factory.Nd4j;

public class LottoAIManager {

	private final int LOOKBACK_WINDOW = 5;
	private final int NUM_CLASSES = 45;

	// 예시용 가상 필터 인터페이스 (기존 자바 필터 구조가 있다면 대체 가능)
	interface LottoFilter {
		boolean filter(List<Integer> numbers);
	}

	// --- [파이프라인 단계 1 & 2: 필터 결과 수치화 및 행렬 변환] ---
	public MultiDataSet buildDatasetPipeline(List<List<Integer>> allLottoHistory, List<LottoFilter> filters) {
		int totalRows = allLottoHistory.size();
		int numFilters = filters.size();

		// 1. 파이썬의 normalized_filter_matrix에 해당하는 2차원 원본 배열 생성
		double[][] rawFilterMatrix = new double[totalRows][numFilters];

		for (int i = 0; i < totalRows; i++) {
			List<Integer> numbers = allLottoHistory.get(i);
			for (int f = 0; f < numFilters; f++) {
				// 파이썬의 1.0 if passed else 0.0과 동일 구조 (엄격한 불리언 검증)
				boolean passed = filters.get(f).filter(numbers);
				rawFilterMatrix[i][f] = passed ? 1.0 : 0.0;
			}
		}

		// 2. StandardScaler 구현 (평균 0, 표준편차 1 정규화)
		double[][] normalizedMatrix = applyStandardScaler(rawFilterMatrix, totalRows, numFilters);

		// 3. 딥러닝 학습용 시퀀스 데이터셋 구성 [Batch, Features, Timesteps]
		int sampleCount = totalRows - this.LOOKBACK_WINDOW;

		INDArray xNum = Nd4j.zeros(sampleCount, this.NUM_CLASSES, this.LOOKBACK_WINDOW);
		INDArray xFilter = Nd4j.zeros(sampleCount, numFilters, this.LOOKBACK_WINDOW);
		INDArray yTrain = Nd4j.zeros(sampleCount, this.NUM_CLASSES);

		for (int i = 0; i < sampleCount; i++) {
			// 과거 5개 회차 윈도우 슬라이딩
			for (int w = 0; w < this.LOOKBACK_WINDOW; w++) {
				List<Integer> historyNumbers = allLottoHistory.get(i + w);

				// 입력 1: 번호 원-핫 인코딩 주입
				for (int num : historyNumbers) {
					xNum.putScalar(new int[] { i, num - 1, w }, 1.0);
				}

				// 입력 2: 정규화된 필터값 주입
				for (int f = 0; f < numFilters; f++) {
					xFilter.putScalar(new int[] { i, f, w }, normalizedMatrix[i + w][f]);
				}
			}

			// 정답 데이터 (다음 회차의 당첨 번호 원-핫 벡터)
			List<Integer> targetNumbers = allLottoHistory.get(i + this.LOOKBACK_WINDOW);
			for (int num : targetNumbers) {
				yTrain.putScalar(new int[] { i, num - 1 }, 1.0);
			}
		}

		return new MultiDataSet(new INDArray[] { xNum, xFilter }, new INDArray[] { yTrain });
	}

	// --- [4단계: AI 확률 기반 대량 샘플링 및 최종 로또 번호 추출] ---
	public List<List<Integer>> generateLottoTickets(double[] probList, List<Integer> selectedNumbers, int totalGames,
			int tried, List<LottoFilter> notOptionFilters) {

		List<List<Integer>> finalTickets = new ArrayList<>();
		int attempts = 0;

		while (attempts < tried) {
			if (finalTickets.size() >= totalGames) {
				break;
			}
			attempts++;

			// 1) AI 가중치(Softmax 확률) 기반 무작위 6개 번호 샘플링
			List<Integer> sampled = sampleWithProbability(selectedNumbers, probList, 6);
			Collections.sort(sampled);

			// 2) 뽑힌 조합이 기존 수십 개의 필수 필터를 모두 안전하게 만족정밀 검증 (Double Check)
			boolean isSafe = true;
			for (LottoFilter lottoFilter : notOptionFilters) {
				if (!lottoFilter.filter(sampled)) {
					isSafe = false;
					break; // 하나라도 통과 못 하면 탈락
				}
			}

			// 3) 중복 체크 후 최종 티켓 저장
			if (isSafe && !finalTickets.contains(sampled)) {
				finalTickets.add(sampled);
			}
		}
		return finalTickets;
	}

	// --- [내장 헬퍼 함수 1: 수치 평탄화 StandardScaler 객체 자체 구현] ---
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

	// --- [내장 헬퍼 함수 2: 넘파이 np.random.choice(p=prob_list) 대응 자바 구현] ---
	private List<Integer> sampleWithProbability(List<Integer> candidates, double[] probabilities, int size) {
		List<Integer> result = new ArrayList<>();
		List<Integer> pool = new ArrayList<>(candidates);
		Random rand = new Random();

		while (result.size() < size && !pool.isEmpty()) {
			double r = rand.nextDouble();
			double cumulativeProbability = 0.0;

			int selectedIdx = -1;
			for (int i = 0; i < pool.size(); i++) {
				cumulativeProbability += probabilities[pool.get(i) - 1]; // 번호 인덱스는 1~45이므로 -1 조정
				if (r <= cumulativeProbability) {
					selectedIdx = i;
					break;
				}
			}

			if (selectedIdx == -1)
				selectedIdx = pool.size() - 1;

			result.add(pool.remove(selectedIdx)); // 중복 추출 방지(replace=False)
		}
		return result;
	}
}
