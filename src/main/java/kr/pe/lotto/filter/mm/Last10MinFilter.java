/**
 * Last10MinFilter.java
 */
package kr.pe.lotto.filter.mm;

import kr.pe.lotto.filter.AMinMaxFilter;
import kr.pe.lotto.util.LottoUtil;
import kr.pe.util.Arr;
import kr.pe.util.Str;

/**
 * <pre>
 * 
 * PC  2026. 9. 2. 오후 1:46:39
 * </pre>
 */
public class Last10MinFilter extends AMinMaxFilter {

	int[] last10;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param value
	 */
	public Last10MinFilter(String name, int value, int[] last10) {
		super(name, value);
		this.last10 = last10;
	}

	@Override
	public boolean filter(int[] nums) {
//		int counts = Arr.contains(nums, this.last10);
//		if (counts < this.value) {
//			LottoUtil.logger.debug("{} counts: {}, value: {} {}", this.name, counts, this.value, Arrays.toString(nums));
//			LottoUtil.logger.debug("{}", Arrays.toString(this.last10));
//		}
//		return this.value <= counts;
		return this.value <= Arr.contains(nums, this.last10);
	}

	/**
	 * @return the last10
	 */
	public int[] getLast10() {
		return this.last10;
	}

	public static void main(String[] args) {
		int[] last10 = new int[] { 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19, 20, 21, 22, 23, 24, 25, 28,
				29, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 43 };
		int[] nums = new int[] { 1, 11, 23, 29, 41, 43 };

		LottoUtil.logger.debug("contains : {}", Arr.contains(nums, last10));
		LottoUtil.logger.debug("contains1 : {}", Arr.contains1(nums, last10));

		int total = 200000000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			Arr.contains(nums, last10);
		}
		long end = System.currentTimeMillis();
		// contains 소요시간: 21초 247밀리초
		LottoUtil.logger.debug("contains 소요시간: {}", Str.timeToString(end - start));

		for (int i = 0; i < total; i++) {
			Arr.contains1(nums, last10);
		}
		end = System.currentTimeMillis();
		// contains1 소요시간: 32초 837밀리초
		LottoUtil.logger.debug("contains1 소요시간: {}", Str.timeToString(end - start));
	}
}
