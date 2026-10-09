/**
 * ConerCountRangeFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * multi3 = [ 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45 ]
 * multi5 = [ 5, 10, 15, 20, 25, 30, 35, 40, 45 ]
 * 
 * PC  2026. 9. 2. 오후 2:06:02
 * </pre>
 */
public class MultipleRangeFilter extends ARangeFilter {

	private int[] multi3 = new int[] { 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45 };
	private int[] multi5 = new int[] { 5, 10, 15, 20, 25, 30, 35, 40, 45 };

	private int[] multi;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public MultipleRangeFilter(String name, int min, int max, int index) {
		super(name, min, max);
		this.multi = (index == 3) ? this.multi3 : this.multi5;
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.multi);
		return this.min <= matchCount && matchCount <= this.max;
	}

}
