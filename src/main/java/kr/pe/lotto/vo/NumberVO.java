/**
 * 
 */
package kr.pe.lotto.vo;

import kr.pe.lotto.LottoNumberException;

/**
 * 
 */
public class NumberVO extends ANumberVO implements Comparable<NumberVO> {

	/**
	 * 
	 */
	public NumberVO() {
		super();
	}

	/**
	 * @param str
	 * @throws LottoNumberException
	 */
	public NumberVO(String str) throws LottoNumberException {
		super(str);
	}

	/**
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 * @param n6
	 * @param bn
	 */
	public NumberVO(int n1, int n2, int n3, int n4, int n5, int n6, int bn) {
		super(n1, n2, n3, n4, n5, n6, bn);
	}

	/**
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 * @param n6
	 */
	public NumberVO(int n1, int n2, int n3, int n4, int n5, int n6) {
		super(n1, n2, n3, n4, n5, n6);
	}

	/**
	 * @param nums
	 */
	public NumberVO(int[] nums) {
		super(nums);
	}

	public NumberVO cloneNumberVO() {
		return new NumberVO(this.nums);
	}

	@Override
	public int compareTo(NumberVO o) {
		return this.toString().compareTo(o.toString());
	}
}
