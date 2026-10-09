/**
 * ACMinFilter.java
 */
package kr.pe.lotto.filter.mm;

import kr.pe.lotto.filter.AMinMaxFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * 삼각 패턴
 * 
 * PC  2026. 8. 30. 오후 1:53:27
 * </pre>
 */
public class TriangleMaxFilter extends AMinMaxFilter {

	public static int maxCount = 4;

	private int[][] grps = new int[][] {
			{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 22, 23, 24, 25, 29, 30, 31, 36, 37, 43 },
			{ 1, 8, 9, 15, 16, 17, 22, 23, 24, 25, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 43, 44, 45 },
			{ 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 25, 26, 27, 28, 33, 34, 35, 41, 42 },
			{ 7, 13, 14, 19, 20, 21, 25, 26, 27, 28, 31, 32, 33, 34, 35, 37, 38, 39, 40, 41, 42, 43, 44, 45 } };

	private int[] tri;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public TriangleMaxFilter(String name, int value, int index) {
		super(name, value);
		this.tri = this.grps[index];
	}

	@Override
	public boolean filter(int[] nums) {
		int matchCount = Arr.contains(nums, this.tri);
		return matchCount < this.value;
	}
}
