/**
 * AMaxFilter.java
 */
package kr.pe.lotto.filter;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:35:17
 * </pre>
 */
public abstract class AMinMaxFilter extends ALottoFilter {

	protected int value;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public AMinMaxFilter(String name, int value) {
		super(name);
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}
}
