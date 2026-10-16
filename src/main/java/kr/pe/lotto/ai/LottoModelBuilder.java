/**
 * LottoModelBuilder.java
 */
package kr.pe.lotto.ai;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 16.
 */
public class LottoModelBuilder {

	public static void main(String[] args) {
//		// --- 하이퍼파라미터 설정 ---
//		int lookbackWindow = 5;
//		int numClasses = 45;
//		int numFilters = 30; // 작성하신 final_filter_matrix.shape[1] 결과 크기 예시
//		int totalSamples = 1000; // 변환할 총 샘플 데이터 수 예시
//
//		// --- 1단계: 딥러닝 학습용 시퀀스 데이터셋 구성 (ND4J 3차원/2차원 배열 빌드) ---
//		// Keras 차원 구조 [Batch_Size, Timesteps, Feature_Size]와 동일하게 ND4J 생성
//		INDArray xNum = Nd4j.zeros(totalSamples, numClasses, lookbackWindow); // DL4J LSTM은 [Batch, Features, Time] 순서가
//																				// 기본 포맷입니다.
//		INDArray xFilter = Nd4j.zeros(totalSamples, numFilters, lookbackWindow);
//		INDArray yTrain = Nd4j.zeros(totalSamples, numClasses); // 정답은 2차원 [Batch, Classes]
//
//		/*
//		 * [데이터 주입 루프 예시] 파이썬의 원-핫 인코딩 루프 작업을 아래와 같이 수행합니다. for (int i = 0; i <
//		 * totalSamples; i++) { for (int w = 0; w < lookbackWindow; w++) {
//		 * xNum.putScalar(new int[]{i, num - 1, w}, 1.0); xFilter.putScalar(new int[]{i,
//		 * filterIdx, w}, normalizedFilterValue); } yTrain.putScalar(new int[]{i,
//		 * targetNum - 1}, 1.0); }
//		 */
//
//		// DL4J의 다중 입출력을 위한 MultiDataSet 패킹
//		MultiDataSet trainingData = new MultiDataSet(new INDArray[] { xNum, xFilter }, // 다중 입력배열
//				new INDArray[] { yTrain } // 출력(정답)배열
//		);
//
//		// --- 2단계: 다중 입력(Multi-Input) 딥러닝 모델 빌드 ---
//		ComputationGraphConfiguration conf = new NeuralNetConfiguration.Builder().seed(12345)
//				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).weightInit(WeightInit.XAVIER)
//				.graphBuilder()
//				// 1) 입력 노드 선언 (Keras의 Input 레이어 역할)
//				.addInputs("Lotto_Numbers", "Filter_Matrices")
//
//				// 2) 입력 1 파트: 번호 흐름 데이터 처리 LSTM (Keras: lstm_num)
//				.addLayer("lstm_num", new LSTM.Builder().nIn(numClasses).nOut(64).activation(Activation.TANH).build(),
//						"Lotto_Numbers")
//
//				// 3) 입력 2 파트: 전처리된 필터 행렬 데이터 처리 LSTM (Keras: lstm_filter)
//				.addLayer("lstm_filter",
//						new LSTM.Builder().nIn(numFilters).nOut(32).activation(Activation.TANH).build(),
//						"Filter_Matrices")
//
//				// 4) 다중 입력 레이어 병합 (Keras의 Concatenate 역할)
//				.addVertex("concatenate", new MergeVertex(), "lstm_num", "lstm_filter")
//
//				// 5) Dense 및 최종 출력 레이어 구성 (파이썬 코드에 명시되지 않은 후반부 예측 레이어 예시)
//				.addLayer("dense_combined", new DenseLayer.Builder().nIn(64 + 32) // 64(lstm_num) + 32(lstm_filter) 합산
//																					// 크기
//						.nOut(64).activation(Activation.RELU).build(), "concatenate")
//
//				.addLayer("output_probability", new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT) // 다중 분류를 위한
//																											// Cross
//																											// Entropy
//						.nIn(64).nOut(numClasses) // 45개 번호별 확률
//						.activation(Activation.SOFTMAX) // 확률 분배용 Softmax
//						.build(), "dense_combined")
//
//				// 최종 출력 지정
//				.setOutputs("output_probability").build();
//
//		// 3단계: 모델 초기화 및 학습 진행
//		ComputationGraph model = new ComputationGraph(conf);
//		model.init();
//
//		System.out.println("✅ DL4J 다중 입력 로또 예측 모델이 성공적으로 빌드되었습니다.");
//		System.out.println(model.summary());

		// model.fit(trainingData); // 💡 파이썬의 model.fit()과 동일하게 학습을 수행하는 명령입니다.
	}
}
