/**
 * FactoryWindowCheckBox.java
 */
package kr.pe.pattern.create.factory.abs.comp;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 4:51:41
 * </pre>
 */
public class WindowFCheckBox implements FCheckBox {

	/**
	 * <pre></pre>
	 * 
	 */
	public WindowFCheckBox() {
		super();
	}

	@Override
	public void render() {
		LogUtil.debugAll(this, "윈도우 체크박스 생성 완료");
	}

}
