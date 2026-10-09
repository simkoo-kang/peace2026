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
public class FrogRangeFilter extends ARangeFilter {

	public static int maxCount = 4;

	private int[][] frogs = new int[][] { { 1, 2, 8, 9, 15, 16, 22, 23, 29, 30, 36, 37, 43, 44 },
			{ 3, 4, 10, 11, 17, 18, 24, 25, 31, 32, 38, 39, 45, 0 },
			{ 4, 5, 11, 12, 18, 19, 25, 26, 32, 33, 39, 40, 0, 0 },
			{ 6, 7, 13, 14, 20, 21, 27, 28, 34, 35, 41, 42, 0, 0 } };
	private int index;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 * @param index
	 */
	public FrogRangeFilter(String name, int min, int max, int index) {
		super(name, min, max);
		this.index = index;
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.frogs[this.index]);
		return (matchCount < this.min || this.max < matchCount);
	}

}
