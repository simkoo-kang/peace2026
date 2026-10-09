/**
 * Circle.java
 */
package kr.pe.pattern.struct.bridge;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 10. 오후 9:13:08
 * </pre>
 */
public class Circle extends Shape {

	/**
	 * <pre></pre>
	 * 
	 * @param color
	 */
	public Circle(Color color) {
		super(color);
	}

	@Override
	public void draw() {
		this.color.applyColor();
		LogUtil.debugAll(this, "원을 그립니다.");
	}
}
