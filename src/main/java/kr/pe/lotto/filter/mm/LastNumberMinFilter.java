/**
 * ACMinFilter.java
 */
package kr.pe.lotto.filter.mm;

import kr.pe.lotto.filter.AMinMaxFilter;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:53:27
 * </pre>
 */
public class LastNumberMinFilter extends AMinMaxFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public LastNumberMinFilter(String name, int value) {
		super(name, value);
	}

	@Override
	public boolean filter(int[] nums) {
		return this.value <= nums[5];
	}
}
