/**
 * ConerCountRangeFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * pairs = [ 11, 22, 33, 44 ]
 * ddang = [ 5, 10, 15, 20, 25, 30, 35, 40, 45 ]
 * 
 * PC  2026. 9. 2. 오후 2:06:02
 * </pre>
 */
public class PairsDdangRangeFilter extends ARangeFilter {

	public static int maxCount = 2;

	private int[] pairs = new int[] { 11, 22, 33, 44 };
	private int[] ddang = new int[] { 13, 18, 31, 38 };
	private int[] arr;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public PairsDdangRangeFilter(String name, int min, int max, int index) {
		super(name, min, max);
		this.arr = (index == 0) ? this.pairs : this.ddang;
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.arr);
		return this.min <= matchCount && matchCount <= this.max;
	}

}
