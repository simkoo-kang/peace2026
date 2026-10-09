/**
 * RedColor.java
 */
package kr.pe.pattern.struct.bridge;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 구체적인 구현 클래스들
 * 
 * PC  2026. 9. 10. 오후 9:09:04
 * </pre>
 */
public class RedColor implements Color {

	@Override
	public void applyColor() {
		LogUtil.debugAll(this, "빨간색");
	}
}
