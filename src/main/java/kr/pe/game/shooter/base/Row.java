/**
 * Row.java
 */
package kr.pe.game.shooter.base;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Row extends BaseElement {

	public Row() {
		super();
	}

	public Row(String... values) {
		this();
		super.addCols(values);
	}
}
