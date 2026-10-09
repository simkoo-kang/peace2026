/**
 * FactoryWindowTextEdit.java
 */
package kr.pe.pattern.create.factory.abs.comp;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 4:55:03
 * </pre>
 */
public class MacFTextEdit implements FTextEdit {

	/**
	 * <pre></pre>
	 * 
	 */
	public MacFTextEdit() {
		super();
	}

	@Override
	public void render() {
		LogUtil.debugAll(this, "맥 텍스트박스 생성 완료");
	}

}
