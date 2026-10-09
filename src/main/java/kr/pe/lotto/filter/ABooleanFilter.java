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
public abstract class ABooleanFilter extends ALottoFilter {

	protected boolean value;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param bool
	 */
	public ABooleanFilter(String name, boolean bool) {
		super(name);
		this.value = bool;
	}

	/**
	 * @return the value
	 */
	public boolean isValue() {
		return this.value;
	}
}
