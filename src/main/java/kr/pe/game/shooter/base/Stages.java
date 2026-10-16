/**
 * Stages.java
 */
package kr.pe.game.shooter.base;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Stages extends BaseLengthElement {

	public Stages() {
		super();
	}

	public Stages(int length, String... values) {
		this();
		setLength(length);
		super.addCols(values);
	}
}
