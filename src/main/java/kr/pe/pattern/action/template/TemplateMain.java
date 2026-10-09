/**
 * TemplateMain.java
 */
package kr.pe.pattern.action.template;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 템플릿 메서드 패턴(Template Method Pattern)은
 *  상위 추상 클래스에서 전체적인 알고리즘의 뼈대(실행 순서)를 정의하고,
 *  구체적인 단계의 구현은 하위 클래스에 위임하는 행위(Behavioral) 디자인 패턴입니다.
 *  상속을 통해 전체적인 틀은 유지하면서 특정 단계의 기능만 다양하게 변경하고 싶을 때 사용합니다.
 * 
 * 💡 핵심 개념
 * 1. 추상 클래스 (Abstract Class):
 *   전체적인 실행 프로세스를 담은 템플릿 메서드를 가지고 있습니다.
 *   이 메서드는 불변의 흐름을 통제합니다.
 *  
 * 2. 훅 메서드 (Hook Method):
 *   하위 클래스에서 선택적으로 오버라이딩할 수 있도록 비워두거나 기본 동작만 구현해 둔 메서드입니다.
 *  
 * 3. 코드 재사용성 극대화:
 *   공통되는 로직은 상위 클래스에 두고, 달라지는 부분만 하위 클래스에서 구현하므로 중복 코드가 획기적으로 줄어듭니다.
 *  
 * 4. 할리우드 원칙 (Hollywood Principle):
 *   "전화하지 마세요, 저희가 연락드릴게요(Don't call us, we'll call you)."
 *   즉, 하위 클래스가 상위 클래스를 직접 호출하지 않고,
 *   상위 클래스가 필요할 때 하위 클래스의 메서드를 호출하는 제어 역전(IoC)이 발생합니다.
 * 
 * ⚖️ 장단점
 * 1. 장점
 *    중복 코드를 제거하고 상위 클래스로 코드를 일원화하여 유지보수가 쉬워집니다.
 *    전체 알고리즘의 핵심 흐름을 강제할 수 있어 안전합니다.
 *    서브클래스의 확장이 필요한 부분(추상 메서드)과 선택적인 부분(훅 메서드)을 명확히 구분할 수 있습니다.
 * 
 * 2. 단점
 *    자바는 단일 상속만 지원하므로, 이 패턴을 쓰면 다른 클래스를 상속받을 수 없어 상속 구조가 복잡해질 수 있습니다.
 *    상위 클래스의 템플릿 메서드가 수정되면 모든 하위 클래스에 영향을 줄 수 있습니다.
 * 
 * PC  2026. 9. 9. 오후 2:14:10
 * </pre>
 */
public class TemplateMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 2:14:10
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		LogUtil.debugAll(TemplateMain.class, "=== 커피 준비 ===");
		CaffeineBeverage coffee = new Coffee();
		coffee.prepareRecipe();

		LogUtil.debugAll(TemplateMain.class, "=== 레몬 없는 차 준비 ===");
		CaffeineBeverage tea = new Tea(false); // 레몬 생략 조건
		tea.prepareRecipe();
	}
}
