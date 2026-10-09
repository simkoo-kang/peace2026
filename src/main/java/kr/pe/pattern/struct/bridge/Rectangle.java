/**
 * Rectangle.java
 */
package kr.pe.pattern.struct.bridge;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 10. 오후 9:14:31
 * </pre>
 */
public class Rectangle extends Shape {

	/**
	 * <pre></pre>
	 * 
	 * @param color
	 */
	public Rectangle(Color color) {
		super(color);
	}

	@Override
	public void draw() {
		this.color.applyColor();
		LogUtil.debugAll(this, "사각형을 그립니다.");
	}
}
