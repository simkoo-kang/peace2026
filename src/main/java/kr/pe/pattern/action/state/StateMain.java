/**
 * StateMain.java
 */
package kr.pe.pattern.action.state;

/**
 * <pre>
 * 상태 패턴(State Pattern)은 객체의 내부 상태 변화에 따라 객체의 행동(Behavior)을
 *  동적으로 변경할 수 있게 해주는 행위(Behavioral) 디자인 패턴입니다.
 * 
 * 상태에 따라 복잡한 조건문(if-else 또는 switch-case)을 사용하는 대신,
 *  상태 자체를 독립된 클래스로 캡슐화하여 객체처럼 다루는 것이 핵심입니다.
 * 
 * 
 * 💡 핵심 개념 및 구성 요소
 * 1. Context (컨텍스트):
 *   상태를 유지하고 관리하는 주체입니다. 클라이언트의 요청을 현재 상태 객체에 위임합니다.
 * 2. State (상태 인터페이스):
 *   모든 상태가 공통으로 구현해야 할 메서드(행동)를 정의합니다.
 * 3. ConcreteState (구체적 상태):
 *   특정 상태에서 수행해야 할 구체적인 행동을 구현합니다. 상태가 바뀔 때 다음 상태로 전이(Transition)하는 로직도 포함합니다.
 * 
 * 
 * ⚖️ 장단점
 * 1. 장점
 *  - 조건문(if-else)이 사라져 코드가 깔끔해지고 가독성이 높아집니다.
 *  - 새로운 상태가 추가되어도 기존 Context나 다른 상태 클래스를 수정할 필요가 없어 개방-폐쇄 원칙(OCP)을 지킬 수 있습니다.
 *  - 상태에 따른 행동이 각 클래스에 명확히 분산되어 유지보수가 쉽습니다.
 * 
 * 2. 단점
 *  - 상태의 수가 늘어날수록 관리해야 할 클래스 파일 개수가 대폭 증가합니다.
 *  - 상태 로직이 단순한 경우에 이 패턴을 적용하면 오히려 과도한 설계(Over-engineering)가 될 수 있습니다.
 * 
 * PC  2026. 9. 9. 오후 3:10:32
 * </pre>
 */
public class StateMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 3:10:32
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Order order = new Order();

		order.printStatus(); // 현재 상태: 결제 완료
		order.nextStep(); // 상품 배송을 시작합니다.

		order.printStatus(); // 현재 상태: 배송 중
		order.nextStep(); // 상품 배송이 완료되었습니다.

		order.printStatus(); // 현재 상태: 배송 완료
	}

}
