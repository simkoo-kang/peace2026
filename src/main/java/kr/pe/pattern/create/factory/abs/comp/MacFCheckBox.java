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
public class MacFCheckBox implements FCheckBox {

	/**
	 * <pre></pre>
	 * 
	 */
	public MacFCheckBox() {
		super();
	}

	@Override
	public void render() {
		LogUtil.debugAll(this, "맥 체크박스 생성 완료");
	}

}
