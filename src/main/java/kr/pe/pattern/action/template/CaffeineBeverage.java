/**
 * CaffeineBeverage.java
 */
package kr.pe.pattern.action.template;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:07:32
 * </pre>
 */
public abstract class CaffeineBeverage {

	// 1. 알고리즘의 뼈대를 정의한 템플릿 메서드 (하위 클래스에서 변경 불가하도록 final 선언)
	public final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		if (customerWantsCondiments()) { // 훅(Hook) 메서드 적용
			addCondiments();
		}
	}

	// 공통 기능 (상위 클래스에서 구현)
	private void boilWater() {
		LogUtil.debugAll(this, "물 끓이는 중...");
	}

	private void pourInCup() {
		LogUtil.debugAll(this, "컵에 따르는 중...");
	}

	// 하위 클래스가 반드시 구현해야 하는 추상 메서드
	protected abstract void brew();

	protected abstract void addCondiments();

	// 훅(Hook) 메서드: 자식 클래스에서 필요에 따라 오버라이딩하여 제어 가능
	protected boolean customerWantsCondiments() {
		return true;
	}
}
