/**
 * ARangeFilter.java
 */
package kr.pe.lotto.filter;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:32:12
 * </pre>
 */
public abstract class ARangeFilter extends ALottoFilter {

	protected int min, max;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public ARangeFilter(String name, int min, int max) {
		super(name);
		this.min = min;
		this.max = max;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return this.min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return this.max;
	}
}
