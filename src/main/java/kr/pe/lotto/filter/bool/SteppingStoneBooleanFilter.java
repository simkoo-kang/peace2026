/**
 * EqualIntervalBooleanFilter.java
 */
package kr.pe.lotto.filter.bool;

import kr.pe.lotto.filter.ABooleanFilter;
import kr.pe.lotto.util.LottoUtil;

/**
 * <pre>
 * [기능 2] '1,2,4' 또는 '1,3,4' 처럼 3개 숫자가 징검다리 형태로 붙어있는지 검사합니다.
 * (정렬했을 때 간격이 [1, 2]이거나 [2, 1]인 연속된 3개의 숫자 그룹이 있는지 확인)
 * 
 * PC  2026. 8. 30. 오후 2:03:39
 * </pre>
 */
public class SteppingStoneBooleanFilter extends ABooleanFilter {

	/**
	 * <pre>
	 * [기능 2] '1,2,4' 또는 '1,3,4' 처럼 3개 숫자가 징검다리 형태로 붙어있는지 검사합니다.
	 * (정렬했을 때 간격이 [1, 2]이거나 [2, 1]인 연속된 3개의 숫자 그룹이 있는지 확인)
	 * </pre>
	 * 
	 * @param name
	 * @param value
	 */
	public SteppingStoneBooleanFilter(String name, boolean value) {
		super(name, value);
	}

	@Override
	public boolean filter(int[] nums) {
		return this.value == LottoUtil.hasSteppingStonePattern(nums);
	}
}
