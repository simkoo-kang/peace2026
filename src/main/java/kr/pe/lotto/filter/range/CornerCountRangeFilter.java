/**
 * ConerCountRangeFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * corners = [ 1, 2, 6, 7, 8, 9, 13, 14, 29, 30, 34, 35, 36, 37, 41, 42, 43, 44, 45 ]
 * 
 * PC  2026. 9. 2. 오후 2:06:02
 * </pre>
 */
public class CornerCountRangeFilter extends ARangeFilter {

	private int[] conrners = new int[] { 1, 2, 6, 7, 8, 9, 13, 14, 29, 30, 34, 35, 36, 37, 41, 42, 43, 44, 45 };

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public CornerCountRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.conrners);
		return this.min <= matchCount && matchCount <= this.max;
	}

}
