/**
 * ConerCountRangeFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * comps = [ 1, 4, 8, 10, 16, 20, 22, 25, 26, 28, 32, 34, 35, 38, 40, 44 ]
 * 
 * PC  2026. 9. 2. 오후 2:06:02
 * </pre>
 */
public class CompositionRangeFilter extends ARangeFilter {

	private int[] comps = new int[] { 1, 4, 8, 10, 16, 20, 22, 25, 26, 28, 32, 34, 35, 38, 40, 44 };

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public CompositionRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.comps);
		return this.min <= matchCount && matchCount <= this.max;
	}

}
