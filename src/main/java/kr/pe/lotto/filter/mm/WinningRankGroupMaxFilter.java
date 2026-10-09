/**
 * ACMinFilter.java
 */
package kr.pe.lotto.filter.mm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.pe.lotto.filter.AMinMaxFilter;
import kr.pe.lotto.util.LottoUtil;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.util.Arr;
import kr.pe.util.Coll;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 1:53:27
 * </pre>
 */
public class WinningRankGroupMaxFilter extends AMinMaxFilter {

	private int[] groupA, groupB, groupC, panelA, panelB, panelC;
	private int[][] total;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public WinningRankGroupMaxFilter(String name, int value, List<NumberInfoVO> vos) {
		super(name, value);

		// 상위 15위
		this.groupA = new int[15];
		// 상위 16위부터 30위까지
		this.groupB = new int[15];
		// 상위 31위부터 45위까지
		this.groupC = new int[15];
		// groupA, groupB, groupC 각 상위 5위까지
		this.panelA = new int[15];
		// groupA, groupB, groupC 각 상위 6위부터 10위까지
		this.panelB = new int[15];
		// groupA, groupB, groupC 각 상위 11위부터 15위까지
		this.panelC = new int[15];

		this.total = new int[][] { this.groupA, this.groupB, this.groupC, this.panelA, this.panelB, this.panelC };

		Map<Integer, Integer> prequencyMap = LottoUtil.getNumberCountMap(vos);
		/**
		 * 빈도수의 역순(내림차순)으로 정렬
		 */
		Map<Integer, Integer> sortedMap = Coll.sortMapByValueReverseOrder(prequencyMap);

		Set<Integer> keys = sortedMap.keySet();
		int index = -1;
		for (Iterator<Integer> it = keys.iterator(); it.hasNext();) {
			Integer key = it.next();
			index++;
			if (index < 15) {
				int idx = index;
				this.groupA[idx] = key;
			} else if (index < 30) {
				int idx = index - 15;
				this.groupB[idx] = key;
			} else {
				int idx = index - 30;
				this.groupC[idx] = key;
			}
		}

		for (index = 0; index < 3; index++) {
			if (index == 0) {
				for (int idx = 0; idx < 5; idx++) {
					this.panelA[idx] = this.groupA[idx];
					this.panelA[idx + 5] = this.groupB[idx];
					this.panelA[idx + 10] = this.groupC[idx];
				}
			} else if (index == 1) {
				for (int idx = 0; idx < 5; idx++) {
					this.panelB[idx] = this.groupA[idx + 5];
					this.panelB[idx + 5] = this.groupB[idx + 5];
					this.panelB[idx + 10] = this.groupC[idx + 5];
				}
			} else {
				for (int idx = 0; idx < 5; idx++) {
					this.panelC[idx] = this.groupA[idx + 10];
					this.panelC[idx + 5] = this.groupB[idx + 10];
					this.panelC[idx + 10] = this.groupC[idx + 10];
				}
			}
		}
	}

	@Override
	public boolean filter(int[] nums) {
		// groupA, groupB, groupC, panelA, panelB, panelC 각 각에 max 보다 많이 있으면 차단.
		int matchCount;
		for (int idx = 0; idx < this.total.length; idx++) {
			matchCount = Arr.contains(nums, this.groupA);
			if (this.value < matchCount) {
				return false;
			}
		}
		// groupB에 0개 있으면, 즉 groupA, groupB에 6개 포함되면 차단.
		matchCount = Arr.contains(nums, this.groupB);
		return 0 < matchCount;
	}
}
