/**
 * 
 */
package kr.pe.lotto.vo;

import kr.pe.util.Str;

/**
 * 
 */
public class CountMoneyVO {

	private int count;
	private long money;

	public CountMoneyVO() {
		this(0, 0);
	}

	/**
	 * @param count
	 * @param money
	 */
	public CountMoneyVO(int count, long money) {
		this.count = count;
		this.money = money;
	}

	public CountMoneyVO clone() {
		CountMoneyVO vo = new CountMoneyVO(this.count, this.money);
		return vo;
	}

	@Override
	public String toString() {
		return Str.numberFormat(this.count) + "\t" + Str.numberFormat(this.money);
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the money
	 */
	public long getMoney() {
		return this.money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(long money) {
		this.money = money;
	}
}
