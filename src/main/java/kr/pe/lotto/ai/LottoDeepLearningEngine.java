/**
 * LottoDeepLearningEngine.java
 */
package kr.pe.lotto.ai;

import java.util.List;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.MultiDataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 16.
 */
public class LottoDeepLearningEngine {

	private final int LOOKBACK_WINDOW = 5; // 과거 5개 회차 분석
	private final int NUM_CLASSES = 45; // 1~45번 번호
	private ComputationGraph model;

	// --- [1단계: 다중 입력(Multi-Input) 신경망 구조 정의] ---
	public void buildMultiInputModel(int numFilters) {
		ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder().seed(42) // 파이썬과 동일한 난수 시드 고정
				.weightInit(WeightInit.XAVIER).graphBuilder()
				// 1. 두 개의 입력단 정의 (Keras의 Input 레이어)
				.addInputs("Lotto_Numbers", "Filter_Matrices")

				// 2. 번호 시퀀스 처리 파트 (Keras: LSTM(64))
				// DL4J RNN 입력 포맷: [Batch, Feature_Size, Time_Steps] -> nIn에는 Feature_Size 대입
				.addLayer("lstm_num",
						new LSTM.Builder().nIn(this.NUM_CLASSES).nOut(64).activation(Activation.TANH).build(),
						"Lotto_Numbers")

				// 3. 필터 지표 시퀀스 처리 파트 (Keras: LSTM(32))
				.addLayer("lstm_filter",
						new LSTM.Builder().nIn(numFilters).nOut(32).activation(Activation.TANH).build(),
						"Filter_Matrices")
				// 4. 두 LSTM 레이어의 출력에서 '마지막 타임스텝 결과(2차원)'만 추출하도록 전처리Vertex 구성
				.addVertex("last_step_num",
						new org.deeplearning4j.nn.conf.graph.rnn.LastTimeStepVertex("Lotto_Numbers"), "lstm_num")
				.addVertex("last_step_filter",
						new org.deeplearning4j.nn.conf.graph.rnn.LastTimeStepVertex("Filter_Matrices"), "lstm_filter")

				// 5. 2차원으로 정제된 두 결과를 병합 (Keras의 Concatenate와 완벽히 일치)
				.addVertex("concatenate", new MergeVertex(), "last_step_num", "last_step_filter")
//				// 4. 두 LSTM 레이어 병합 (Keras: Concatenate)
//				.addVertex("concatenate", new MergeVertex(), "lstm_num", "lstm_filter")

				// 5. 출력층 구성 (파이썬 후속 조치에 대응하는 완전한 Dense/Output 구조)
				.addLayer("dense_combined",
						new DenseLayer.Builder().nIn(64 + 32).nOut(64).activation(Activation.RELU).build(),
						"concatenate")

				.addLayer("output_probability",
						new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT).nIn(64).nOut(this.NUM_CLASSES)
								.activation(Activation.SOFTMAX) // 번호별 가중치 확률 출력
								.build(),
						"dense_combined")

				.setOutputs("output_probability").build();

		this.model = new ComputationGraph(conf);
		this.model.init();
		System.out.println("✅ 자바 기반 다중 입력 LSTM 모델 구성 완료");
	}

	// --- [2단계: 학습 데이터 생성 및 시점 교정 파이프라인] ---
	public MultiDataSet prepareTrainingData(List<List<Integer>> rawNumbers, double[][] normalizedFilterMatrix) {
		int totalRows = rawNumbers.size();
		int numFilters = normalizedFilterMatrix[0].length;
		int sampleCount = totalRows - this.LOOKBACK_WINDOW;

		// ND4J 3차원 배열 생성 (순서 주의: [샘플 수, 피처 수, 타임스텝 수])
		INDArray xNum = Nd4j.zeros(sampleCount, this.NUM_CLASSES, this.LOOKBACK_WINDOW);
		INDArray xFilter = Nd4j.zeros(sampleCount, numFilters, this.LOOKBACK_WINDOW);
		INDArray yTrain = Nd4j.zeros(sampleCount, this.NUM_CLASSES);

		for (int i = 0; i < sampleCount; i++) {
			// 과거 5개 회차 윈도우 슬라이딩 연산
			for (int w = 0; w < this.LOOKBACK_WINDOW; w++) {
				// 입력 1: 당첨 번호 원-핫 벡터 인코딩 주입
				List<Integer> historyNumbers = rawNumbers.get(i + w);
				for (int num : historyNumbers) {
					// i번째 샘플의, (num-1)번째 번호 칸의, w번째 시간축 자리에 1.0 주입
					xNum.putScalar(new int[] { i, num - 1, w }, 1.0);
				}

				// 입력 2: 정규화된 필터 행렬 데이터 주입
				for (int f = 0; f < numFilters; f++) {
					xFilter.putScalar(new int[] { i, f, w }, normalizedFilterMatrix[i + w][f]);
				}
			}

			// 정답 데이터 정의 (Keras 루프와 정확히 대응: 다음 회차의 당첨 번호 원-핫 벡터)
			List<Integer> targetNumbers = rawNumbers.get(i + this.LOOKBACK_WINDOW);
			for (int num : targetNumbers) {
				yTrain.putScalar(new int[] { i, num - 1 }, 1.0);
			}
		}

		// DL4J 전용 다중 데이터셋 객체로 패킹
		return new MultiDataSet(new INDArray[] { xNum, xFilter }, new INDArray[] { yTrain });
	}

	// --- [3단계: 모델 학습 실행] ---
	public void trainModel(MultiDataSet trainingData, int epochs) {
		System.out.println("🚀 자바 모델 학습 시작...");
		for (int i = 0; i < epochs; i++) {
			this.model.fit(trainingData);
			if ((i + 1) % 10 == 0) {
				System.out.println("Epoch " + (i + 1) + " / " + epochs + " 완료");
			}
		}
	}

	// --- [4단계: 최신 데이터를 기반으로 최종 확률 리스트(prob_list) 생성] ---
	public double[] predictNextProbability(List<List<Integer>> last5Numbers, double[][] last5Filters) {
		int numFilters = last5Filters[0].length;

		// 예측용 단일 배치(Batch Size = 1) 공간 할당
		INDArray testNum = Nd4j.zeros(1, this.NUM_CLASSES, this.LOOKBACK_WINDOW);
		INDArray testFilter = Nd4j.zeros(1, numFilters, this.LOOKBACK_WINDOW);

		for (int w = 0; w < this.LOOKBACK_WINDOW; w++) {
			for (int num : last5Numbers.get(w)) {
				testNum.putScalar(new int[] { 0, num - 1, w }, 1.0);
			}
			for (int f = 0; f < numFilters; f++) {
				testFilter.putScalar(new int[] { 0, f, w }, last5Filters[w][f]);
			}
		}

		// 순방향 연산(Inference) 수행
		INDArray[] outputs = this.model.output(testNum, testFilter);
		INDArray probabilityVector = outputs[0]; // [1, 45] 형태의 Softmax 결과 배열

		// ND4J 배열을 자바 double 기본 배열로 변환하여 반환 (1번부터 45번 번호별 예측 확률)
		double[] probList = new double[this.NUM_CLASSES];
		for (int i = 0; i < this.NUM_CLASSES; i++) {
			probList[i] = probabilityVector.getDouble(0, i);
		}
		return probList;
	}
}
