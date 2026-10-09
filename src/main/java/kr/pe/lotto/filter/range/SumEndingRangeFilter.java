/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;

/**
 * <pre>
 * 일의 자리 숫자의 총합을 기준으로 필터링
 * 
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class SumEndingRangeFilter extends ARangeFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public SumEndingRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int endingSum = 0;
		for (int i = 0; i < 6; i++) {
			endingSum += nums[i] % 10;
		}
		return this.min <= endingSum && endingSum <= this.max;
	}

}
