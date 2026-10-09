/**
 * CheckWin.java
 */
package kr.pe.lotto.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import kr.pe.util.Arr;

/**
 * <pre></pre>
 * gbrai  2026. 10. 9.
 */
public class CheckWin {

	private Set<Integer> winSet = null; // use in check function
	private int bonus;

	/**
	 * <pre></pre>
	 * @param winNums
	 * @param bonus
	 */
	public CheckWin(int[] winNums, int bonus) {
		this.winSet = Arrays.stream(winNums).boxed().collect(Collectors.toSet());
		this.bonus = bonus;
	}

	/**
	 * check lotto result
	 * 
	 * @param myNumbers
	 * @param bonusNumber
	 * @return
	 */
	public int check(int[] myNumbers) {
		int matchCount = 0;

		// 2. 내 번호와 당첨 번호 비교
		for (int n = 0; n < 6; n++) {
			if (this.winSet.contains(myNumbers[n])) {
				matchCount++;
			}
		}

		int rank = 0;
		// 3. 등수 판정 로직
		switch (matchCount) {
		case 6:
			rank = 1;
			break;
		case 5:
			if(Arr.contains(myNumbers, this.bonus)) {
				rank = 2;
			} else {
				rank = 3;
			}
			break;
		case 4:
			rank = 4;
			break;
		case 3:
			rank = 5;
			break;
		default:
			rank = 0;
		}
		return rank;
	}
}
