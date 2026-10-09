/**
 * 
 */
package kr.pe.lotto.vo;

import kr.pe.lotto.LottoNumberException;
import kr.pe.util.Str;

/**
 * 상속할 때 사용
 */
public abstract class ANumberVO {

	protected int[] nums;

	public ANumberVO() {
		this.nums = new int[7];
	}

	/**
	 * numbers contain bonus or not
	 * 
	 * @param nums
	 */
	public ANumberVO(int[] nums) {
		this();
		for (int i = 0; i < nums.length; i++) {
			this.nums[i] = nums[i];
		}
	}

	public ANumberVO(String str) throws LottoNumberException {
		this();
		if (Str.isEmpty(str)) {
			throw new LottoNumberException("number string is null");
		}
		String[] sps = str.split("[ \t]");
		String[] ss = sps[0].split("-");
		if (ss.length < 6) {
			throw new LottoNumberException("There are not enough numbers.");
		}
		for (int i = 0; i < 6; i++) {
			this.nums[i] = Integer.parseInt(ss[i]);
		}
	}

	/**
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 * @param n6
	 */
	public ANumberVO(int n1, int n2, int n3, int n4, int n5, int n6) {
		this(n1, n2, n3, n4, n5, n6, 0);
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
	public ANumberVO(int n1, int n2, int n3, int n4, int n5, int n6, int bn) {
		this();
		int idx = 0;
		this.nums[idx++] = n1;
		this.nums[idx++] = n2;
		this.nums[idx++] = n3;
		this.nums[idx++] = n4;
		this.nums[idx++] = n5;
		this.nums[idx++] = n6;
		this.nums[idx++] = bn;
	}

	public void sort() {
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 6; j++) {
				if (this.nums[j] < this.nums[i]) {
					int tmp = this.nums[i];
					this.nums[i] = this.nums[j];
					this.nums[j] = tmp;
				}
			}
		}
	}

	@Override
	public String toString() {
		return toString(false);
	}

	/**
	 * @param bonus Whether bonus numbers are included
	 * @return
	 */
	public String toString(boolean bonus) {
		StringBuilder sb = new StringBuilder();
		String cma = "";
		for (int i = 0; i < 6; i++) {
			sb.append(cma).append(Str.fill0(this.nums[i]));
			cma = "-";
		}
		if (bonus && this.nums[6] > 0) {
			sb.append("\t").append(Str.fill0(this.nums[6]));
		}
		return sb.toString();
	}

	/**
	 * 01-02-03-04-05-06 [bonums] sum
	 * 
	 * @return
	 */
	public String toStringSum() {
		return toStringSum(false);
	}

	/**
	 * @param bonus Whether bonus numbers are included
	 * @return
	 */
	public String toStringSum(boolean bonus) {
		StringBuilder sb = new StringBuilder();
		String cma = "";
		for (int i = 0; i < 6; i++) {
			sb.append(cma).append(Str.fill0(this.nums[i]));
			cma = "-";
		}
		if (bonus && this.nums[6] > 0) {
			sb.append("\t").append(Str.fill0(this.nums[6]));
		}
		sb.append("\t").append(this.getSum());
		return sb.toString();
	}

	/**
	 * sum siz numbers
	 * 
	 * @return
	 */
	public int getSum() {
		int s = 0;
		for (int i = 0; i < 6; i++) {
			s += this.nums[i];
		}
		return s;
	}

	/**
	 * count small than 23
	 * 
	 * @return
	 */
	public int getSmall() {
		int cnt = 0;
		for (int i = 0; i < 6; i++) {
			if (this.nums[i] < 23)
				cnt++;
			else
				break;
		}
		return cnt;
	}

	/**
	 * count even number
	 * 
	 * @return
	 */
	public int getEven() {
		int cnt = 0;
		for (int i = 0; i < 6; i++) {
			if (this.nums[i] % 2 == 0)
				cnt++;
		}
		return cnt;
	}

	/**
	 * @return the nums
	 */
	public int[] getNums6() {
		int[] ns = new int[6];
		System.arraycopy(this.nums, 0, ns, 0, 6);
		return ns;
	}

	/**
	 * @return the nums
	 */
	public int[] getNums() {
		return this.nums;
	}

	/**
	 * @param idx
	 * @return
	 */
	public int getNum(int idx) {
		return this.nums[idx];
	}

	/**
	 * @param nums the nums to set
	 */
	public void setNums(int[] nums) {
		this.nums = nums;
	}

	public void setNum(int n, int index) {
		this.nums[index] = n;
	}

	/**
	 * @return
	 */
	public int getBonus() {
		return this.nums[6];
	}
}
