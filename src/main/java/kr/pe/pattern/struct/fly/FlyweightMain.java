/**
 * FlyweightMain.java
 */
package kr.pe.pattern.struct.fly;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 소프트웨어 디자인 패턴에서 플라이웨이트 패턴(Flyweight Pattern)은 동일하거나 유사한 객체들을 공유하여 메모리 사용량을 최소화하는 구조적 패턴입니다.
 *  복싱의 가장 가벼운 체급인 '플라이급(Flyweight)'에서 유래한 이름처럼, 객체를 가볍게 만들어 많은 수의 인스턴스를 효율적으로 다룰 때 사용합니다.
 * 
 * 
 * 💡 핵심 개념: 상태의 분리
 *   플라이웨이트 패턴을 구현할 때는 객체의 상태(속성)를 두 가지로 분리합니다.
 * 
 * 1. 본질적 상태 (Intrinsic State):
 *   객체 내부에서 공유할 수 있는 변하지 않는 데이터입니다. (예: 나무의 3D 모델, 텍스처 데이터)
 * 
 * 2. 외재적 상태 (Extrinsic State):
 *   인스턴스마다 달라지는 변하는 데이터로, 공유할 수 없어 외부에서 전달받아야 합니다.
 *    (예: 나무의 심겨진 위치 X, Y 좌표)
 * 
 * 
 * 🏗️ 주요 구성 요소
 * 1. Flyweight (플라이웨이트 인터페이스):
 *   공유 객체가 구현해야 하는 인터페이스를 정의합니다.
 * 2. ConcreteFlyweight (구체적인 공유 객체):
 *   본질적 상태(공유 데이터)를 가지고 있으며, 외재적 상태를 매개변수로 받아 동작합니다.
 * 3. FlyweightFactory (팩토리 클래스):
 *   기존에 생성된 플라이웨이트 객체를 캐싱(Cache)하고 관리합니다. 만약 요청한 객체가 이미 있으면 기존 객체를 반환하고, 없으면 새로 생성하여 저장 후 반환합니다.
 * 
 * 
 * ⚖️ 장점과 단점
 * 1. 장점 ⭕
 *  - 메모리 절감: 수많은 객체를 만들 때 발생하는 RAM 사용량을 극적으로 줄입니다.
 *  - 성능 향상: 반복적인 new 연산 및 가비지 컬렉션(GC) 부담이 감소합니다.
 * 2. 단점 ❌
 *  - 코드 복잡도 증가: 상태를 분리하고 팩토리를 관리해야 하므로 구조가 복잡해집니다.
 *  - 런타임 계산 비용: 매번 외부에서 상태를 계산해 넘겨주어야 하므로 CPU 연산이 늘어날 수 있습니다.
 * 
 * 
 * 🎯 주로 사용되는 곳
 * 1. 게임 엔진: 화면에 쏟아지는 수천 발의 총알, 수많은 나무나 풀잎 등을 표현할 때 (GPU 인스턴싱 기술과 일맥상통).
 * 2. 텍스트 에디터: 문서 안의 수백만 개 글자(Character)마다 폰트, 크기 정보를 다 따로 만들지 않고 폰트 객체를 공유할 때.
 * 3. 시스템 캐싱 기법: 자바의 String Constant Pool이나 Integer.valueOf() 내부 캐싱 메커니즘.
 * 
 * PC  2026. 9. 10. 오후 8:18:37
 * </pre>
 */
public class FlyweightMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 8:18:37
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 1만 개의 나무를 심지만, 실제 생성되는 무거운 Model 객체는 단 2개뿐입니다.
		for (int i = 0; i < 5000; i++) {
			TreeModel oak = TreeFactory.getTreeModel("OakMesh", "OakTexture");
			oak.render(i, i * 2); // 외재적 상태(좌표)는 실행할 때 전달
		}

		for (int i = 0; i < 5000; i++) {
			TreeModel pine = TreeFactory.getTreeModel("PineMesh", "PineTexture");
			pine.render(i, i * 3);
		}
		LogUtil.debugAll(FlyweightMain.class, "{}", "test logger");
	}
}
