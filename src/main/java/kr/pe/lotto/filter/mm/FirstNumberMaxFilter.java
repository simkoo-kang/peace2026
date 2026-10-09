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
public class FirstNumberMaxFilter extends AMinMaxFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public FirstNumberMaxFilter(String name, int value) {
		super(name, value);
	}

	@Override
	public boolean filter(int[] nums) {
		return nums[0] <= this.value;
	}
}
