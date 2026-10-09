/**
 * EqualIntervalBooleanFilter.java
 */
package kr.pe.lotto.filter.bool;

import kr.pe.lotto.filter.ABooleanFilter;
import kr.pe.lotto.util.LottoUtil;

/**
 * <pre>
 * 1-3-5, 3-6-9, 12-16-20 같은 차이
 * 1-9-17, 6-12-18 대각선
 * 
 * PC  2026. 8. 30. 오후 2:03:39
 * </pre>
 */
public class EqualIntervalBooleanFilter extends ABooleanFilter {

	private int interval;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 * @param interval
	 */
	public EqualIntervalBooleanFilter(String name, boolean bool, int interval) {
		super(name, bool);
		this.interval = interval;
	}

	/**
	 * <pre>
	 * interval = 0이면 모든 동일 간격
	 * </pre>
	 * 
	 * @param name
	 * @param value
	 */
	public EqualIntervalBooleanFilter(String name, boolean value) {
		super(name, value);
		this.interval = 0;
	}

	@Override
	public boolean filter(int[] nums) {
		return this.value == LottoUtil.hasEqualIntervalPattern(nums, this.interval);
	}
}
