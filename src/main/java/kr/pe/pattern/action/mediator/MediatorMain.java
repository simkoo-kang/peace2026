/**
 * MediatorMain.java
 */
package kr.pe.pattern.action.mediator;

/**
 * <pre>
 * 중재자 패턴(Mediator Pattern)은 객체들 간의 복잡한 상호작용(의존성)을 설계를 변경하여 캡슐화하는 행위(Behavioral) 디자인 패턴입니다.
 * 
 * 객체들이 서로를 직접 참조하지 않도록 중간에 '중재자' 객체를 두고, 모든 통신은 이 중재자를 통해서만 이루어지게 만듭니다.
 *  이를 통해 객체 간의 결합도(Coupling)를 획기적으로 낮출 수 있습니다.
 * 
 * 
 * 💡 핵심 개념: M:N 관계를 1:N 관계로 변환
 * 1. 기존 구조 (Spaghetti Code):
 *    여러 객체가 서로 얽혀서 직접 신호를 주고받으면 (M:N 관계), 하나의 객체를 수정할 때 다른 객체들까지 연쇄적으로 수정해야 하는 문제가 생깁니다.
 * 2. 패턴 적용 후:
 *    모든 객체는 오직 중재자(Mediator)만 바라봅니다 (1:N 관계).
 *     상태가 변하면 중재자에게 알리고, 중재자가 판단하여 다른 객체들에 명령을 내립니다.
 * 
 * 
 * ⚖️ 장단점
 * 1. 장점
 *  - 결합도 감소:
 *    동료 클래스(Airplane) 간의 상호작용을 단일 클래스(Mediator)로 집중시켜 클래스 간의 복잡성을 줄입니다.
 *  - 재사용성 향상:
 *    동료 클래스들이 독립적이기 때문에 다른 컨텍스트에서 쉽게 재사용할 수 있습니다.
 *    
 * 2. 단점
 *  - 중재자 독점(God Object) 위험:
 *    시스템이 복잡해질수록 모든 상호작용 로직이 중재자 한곳으로 몰려, 중재자 클래스 자체가 지나치게 비대해지고 유지보수하기 힘들어질 수 있습니다.
 * 
 * 
 * 🔍 옵저버(Observer) 패턴과의 차이점
 * 1. 옵저버 패턴:
 *    상태 변화에 따른 '단방향' 알림(1:N)에 초점이 맞춰져 있으며, 관찰 대상(Subject)과 관찰자(Observer) 관계가 유기적입니다.
 * 
 * 2. 중재자 패턴:
 *    여러 객체 간의 복잡한 '양방향' 소통(M:N)을 끊어내고 중앙 제어(1:N)하는 것에 목적이 있습니다.
 *     (종종 중재자를 구현할 때 옵저버 패턴을 융합해 쓰기도 합니다.)
 * 
 * PC  2026. 9. 9. 오후 5:47:14
 * </pre>
 */
public class MediatorMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 5:47:14
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ControlTower tower = new IncheonControlTower();

		Airplane k購747 = new CommercialAirplane(tower, "대한항공 747");
		Airplane asiana320 = new CommercialAirplane(tower, "아시아나 320");
		Airplane jeju737 = new CommercialAirplane(tower, "제주항공 737");

		tower.registerAirplane(k購747);
		tower.registerAirplane(asiana320);
		tower.registerAirplane(jeju737);

		// 대한항공이 메시지를 보내면 관제탑이 다른 비행기들에 중재하여 전달함
		k購747.send("지금 1번 활주로로 착륙하겠습니다.");
	}

}
