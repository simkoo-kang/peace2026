/**
 * Shape.java
 */
package kr.pe.pattern.struct.bridge;

/**
 * <pre>
 * 모양을 나타내는 추상 클래스
 * 
 * PC  2026. 9. 10. 오후 9:10:53
 * </pre>
 */
public abstract class Shape {

	// 구현부(Color)를 참조하여 '다리(Bridge)' 역할을 합니다.
	protected Color color;

	protected Shape(Color color) {
		this.color = color;
	}

	public abstract void draw();
}
