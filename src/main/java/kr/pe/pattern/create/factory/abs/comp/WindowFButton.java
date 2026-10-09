/**
 * FactoryWindowButton.java
 */
package kr.pe.pattern.create.factory.abs.comp;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 4:45:27
 * </pre>
 */
public class WindowFButton implements FButton {

	/**
	 * <pre></pre>
	 * 
	 */
	public WindowFButton() {
		super();
	}

	@Override
	public void render() {
		LogUtil.debugAll(this, "윈도우 버튼 생성 완료");
	}

}
