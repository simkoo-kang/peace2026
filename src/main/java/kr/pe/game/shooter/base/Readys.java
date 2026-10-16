/**
 * Readys.java
 */
package kr.pe.game.shooter.base;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Readys extends BaseLengthElement {

	/**
	 * <pre></pre>
	 */
	public Readys() {
		super();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 * @param values
	 */
	public Readys(int length, String... values) {
		this();
		setLength(length);
		super.addCols(values);
	}
}
