/**
 * Tea.java
 */
package kr.pe.pattern.action.template;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:10:56
 * </pre>
 */
public class Tea extends CaffeineBeverage {

	private boolean wantsCondiments = false;

	/**
	 * <pre></pre>
	 * 
	 * @param wantsCondiments
	 */
	public Tea(boolean wantsCondiments) {
		super();
		this.wantsCondiments = wantsCondiments;
	}

	@Override
	protected void brew() {
		LogUtil.debugAll(this, "찻잎을 우려내는 중...");
	}

	@Override
	protected void addCondiments() {
		LogUtil.debugAll(this, "레몬을 추가하는 중...");
	}

	@Override
	protected boolean customerWantsCondiments() {
		return this.wantsCondiments;
	}
}
