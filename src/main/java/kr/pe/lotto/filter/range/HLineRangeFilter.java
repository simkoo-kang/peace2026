/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * 한 줄에 최대 maxLines개까지만 허용
 * 공이 들어있는 라인 수가 min <= count <= max 개 안인지 검사합니다.
 * 
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class HLineRangeFilter extends ARangeFilter {

	private int maxLines;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 * @param maxLines
	 */
	public HLineRangeFilter(String name, int min, int max, int maxLines) {
		super(name, min, max);
		this.maxLines = maxLines;
	}

	@Override
	public boolean filter(int[] nums) {
		int[] lines = new int[7];
		for (int i = 0; i < 6; i++) {
			int r = (nums[i] - 1) / 7;
			lines[r]++;
		}
		int maxLine = Arr.max(lines);
		if (this.maxLines < maxLine) {
			return false;
		}

		int count = 6 - Arr.containsZero(lines);
		return this.min <= count && count <= this.max;
	}

}
