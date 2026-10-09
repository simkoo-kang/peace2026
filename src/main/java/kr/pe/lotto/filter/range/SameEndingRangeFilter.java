/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * 로또 번호의 1의 자리 숫자(끝수) 중 '가장 많이 중복된 개수'가 지정한 범위(min ~ max) 안인지 검사합니다.
 *  - 기본값 (1 ~ 2): 2동끝수(2개 중복) 또는 3동끝수(3개 중복)가 포함된 조합만 통과
 * 
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class SameEndingRangeFilter extends ARangeFilter {

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 */
	public SameEndingRangeFilter(String name, int min, int max) {
		super(name, min, max);
	}

	@Override
	public boolean filter(int[] nums) {
		int[] endings = new int[10];
		for (int i = 0; i < 6; i++) {
			endings[nums[i] % 10]++;
		}
		int maxEnding = Arr.max(endings);
		return this.min <= maxEnding && maxEnding <= this.max;
	}

}
