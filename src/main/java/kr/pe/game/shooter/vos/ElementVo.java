/**
 * RowVo.java
 */
package kr.pe.game.shooter.vos;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public abstract class ElementVo {

	protected String[] cols;

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 */
	public ElementVo(int length) {
		super();
		this.cols = new String[length];
	}

	/**
	 * <pre></pre>
	 * 
	 * @param cols
	 */
	public ElementVo(String[] cols) {
		this(cols.length);
		setCols(cols);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param index
	 * @return
	 */
	public String getCol(int index) {
		if (0 <= index && index < this.cols.length) {
			return this.cols[index];
		}
		return null;
	}

	public void setCol(int index, String col) {
		if (0 <= index && index < this.cols.length) {
			this.cols[index] = col;
		}
	}

	/**
	 * @return the cols
	 */
	public String[] getCols() {
		return this.cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(String... cols) {
		for (int idx = 0; idx < cols.length; idx++) {
			this.cols[idx] = cols[idx];
		}
	}
}
