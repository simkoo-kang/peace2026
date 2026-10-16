/**
 * ElementLengthVo.java
 */
package kr.pe.game.shooter.vos;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public class ElementLengthVo extends ElementVo {

	protected int length;

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 */
	public ElementLengthVo(int length) {
		super(length);
		this.length = length;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 * @param cols
	 */
	public ElementLengthVo(int length, String... cols) {
		super(cols);
		this.length = length;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
}
