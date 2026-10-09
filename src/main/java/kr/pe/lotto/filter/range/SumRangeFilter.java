/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class SumRangeFilter extends ARangeFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public SumRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += nums[i];
		}
		return this.min <= sum && sum <= this.max;
	}

}
