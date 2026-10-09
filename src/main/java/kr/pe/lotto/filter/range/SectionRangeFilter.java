/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * ** 5구간 ---
 * 통상적으로 당첨 번호 6개는 이 5개 구간 중 3~4개 구간에 골고루 분산되어 출현합니다.
 * 추천 필터링:
 *  - 특정 한 구간에서만 4개 이상의 숫자가 몰려 나오거나,
 *      5개 구간 중 3개 이상의 구간이 통째로 멸(비어 있는 상태)하는 조합은 확률이 낮으므로 제거합니다.
 *      (가장 안정적인 형태는 구간별 출현 개수가 2:2:1:1:0 이나 2:1:1:1:1 구조임)
 * 
 * 구간별 한 구간에 최대 갯수 확인
 * section: 5 start ------------
 *  - 2 = 830 rate = 0.72
 *  - 1 = 192 rate = 0.17
 *  - 3 = 124 rate = 0.11
 *  - 4 = 6 rate = 0.01
 * 
 * ** 9구간 ---
 * 총 9개 구간 중 당첨 번호가 들어가는 구간은 보통 4~5개 구간입니다.
 * 추천 필터링:
 *  - 9개 구간 중 번호가 하나도 나오지 않는 '멸(빈) 구간'이 보통 4~5개 발생하는 것이 통계적으로 정상입니다.
 *      만약 내가 뽑은 조합이 9개 구간에 1개씩 너무 예쁘게 흩어져 있거나(멸 구간 3개 이하),
 *      반대로 한두 구간에 6개 번호가 다 몰려 있다면(멸 구간 7개 이상) 필터에서 제외하는 것이 좋습니다.
 * 
 * 구간별 한 구간에 최대 갯수 확인
 * section: 9 start ------------
 *  - 2 = 713 rate = 0.62
 *  - 3 = 376 rate = 0.33
 *  - 4 = 58 rate = 0.05
 *  - 5 = 5 rate = 0.0
 *  
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class SectionRangeFilter extends ARangeFilter {

	private int section;
	private int maxCount;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min      최소 구간 갯수
	 * @param max      최대 구간 갯수
	 * @param maxCount 한 구간에 포함될 수 있는 최대 갯수
	 * @param section  구간[5 or 9]
	 */
	public SectionRangeFilter(String name, int min, int max, int maxCount, int section) {
		super(name, min, max);
		this.maxCount = maxCount;
		this.section = section;
	}

	@Override
	public boolean filter(int[] nums) {
		int[] sections = new int[10];
		for (int i = 0; i < 6; i++) {
			sections[(nums[i] - 1) / this.section]++;
		}
		if (this.maxCount < Arr.max(sections)) {
			return false;
		}
		int cnts = sections.length - Arr.containsZero(sections);
		return this.min <= cnts && cnts <= this.max;
	}
}
