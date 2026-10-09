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
public class EvenRangeFilter extends ARangeFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public EvenRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int cnt = 0;
		for (int i = 0; i < 6; i++) {
			if (nums[i] % 2 == 0) {
				cnt++;
			}
		}
		return this.min <= cnt && cnt <= this.max;
	}

}
