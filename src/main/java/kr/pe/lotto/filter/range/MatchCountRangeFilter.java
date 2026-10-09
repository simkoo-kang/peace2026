/**
 * MatchCountFilter.java
 */
package kr.pe.lotto.filter.range;

import kr.pe.lotto.filter.ARangeFilter;
import kr.pe.util.Arr;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:46:19
 * </pre>
 */
public class MatchCountRangeFilter extends ARangeFilter {

	private int[] matchs;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param min
	 * @param max
	 * @param matchs
	 */
	public MatchCountRangeFilter(String name, int min, int max, int[] matchs) {
		super(name, min, max);
		this.matchs = matchs;
	}

	@Override
	public boolean filter(int[] nums) {
		int count = Arr.contains(this.matchs, nums);
		return this.min <= count && count <= this.max;
	}

}
