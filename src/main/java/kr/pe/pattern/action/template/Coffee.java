/**
 * Coffee.java
 */
package kr.pe.pattern.action.template;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:09:17
 * </pre>
 */
public class Coffee extends CaffeineBeverage {

	@Override
	protected void brew() {
		LogUtil.debugAll(this, "필터를 통해 커피를 우려내는 중...");
	}

	@Override
	protected void addCondiments() {
		LogUtil.debugAll(this, "설탕과 우유를 추가하는 중...");
	}
}
