/**
 * ACMinFilter.java
 */
package kr.pe.lotto.filter.mm;

import kr.pe.lotto.filter.AMinMaxFilter;

/**
 * <pre>
 * 연속된 숫자 쌍이 0~1개인 조합만 허용, 1,2,3 형식도 차단
 * 
 * PC  2026. 8. 30. 오후 1:53:27
 * </pre>
 */
public class ConsecutivePairsMaxFilter extends AMinMaxFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public ConsecutivePairsMaxFilter(String name, int value) {
		super(name, value);
	}

	@Override
	public boolean filter(int[] nums) {
		int pairCount = 0;
		for (int i = 0; i < 5; i++) {
			if (nums[i + 1] - nums[i] == 1) {
				pairCount++;
			}
		}
		return pairCount <= this.value;
	}
}
