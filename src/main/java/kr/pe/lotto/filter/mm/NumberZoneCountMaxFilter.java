/**
 * ACMinFilter.java
 */
package kr.pe.lotto.filter.mm;

import kr.pe.lotto.filter.AMinMaxFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * 숫자 영역 카운트 범위 필터 ---
 * 로또 번호의 번호대별 수량(개수)을 체크하여 특정 번호대에 숫자가 과도하게 몰리거나
 * 전멸하는 조합을 걸러내는 필터 클래스입니다.
 * 일반적으로 로또는 1번대(1~10), 10번대(11~20), 20번대(21~30), 30번대(31~40), 40번대(41~45)의 5개 번호대로 나뉩니다.
 * 한 번호대에 숫자가 4개 이상 몰리거나, 번호대 3개 이상이 동시에 전멸(멸)하는 조합을 필터링하도록 설계했습니다.
 * 
 * PC  2026. 8. 30. 오후 1:53:27
 * </pre>
 */
public class NumberZoneCountMaxFilter extends AMinMaxFilter {

	private int maxEmptyZones;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 * @param maxEmptyZones
	 */
	public NumberZoneCountMaxFilter(String name, int value, int maxEmptyZones) {
		super(name, value);
		this.maxEmptyZones = maxEmptyZones;
	}

	@Override
	public boolean filter(int[] nums) {
		int[] zoneCounts = new int[5];
		for (int i = 0; i < 6; i++) {
			if (nums[i] < 11) {
				zoneCounts[0]++;
			} else if (nums[i] < 21) {
				zoneCounts[1]++;
			} else if (nums[i] < 31) {
				zoneCounts[2]++;
			} else if (nums[i] < 41) {
				zoneCounts[3]++;
			} else {
				zoneCounts[4]++;
			}
		}
		if (this.value < Arr.max(zoneCounts)) {
			return false;
		}
		int count = 0;
		for (int z : zoneCounts) {
			if (z == 0) {
				count++;
			}
		}
		return count <= this.maxEmptyZones;
	}
}
