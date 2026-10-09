/**
 * 
 */
package kr.pe.lotto.vo;

import java.util.ArrayList;
import java.util.List;

import kr.pe.lotto.LottoNumberException;
import kr.pe.lotto.util.LottoUtil;
import kr.pe.util.Str;

/**
 * 
 */
public class NumberInfoVO extends ANumberVO implements Comparable<NumberInfoVO> {

	protected int round;
	protected String date;
	protected List<CountMoneyVO> list;
	protected int n1Auto, n1Manu, n1Half;
	protected int ac, last10, last05;

	/**
	 * 
	 */
	public NumberInfoVO() {
		super();
		init();
	}

	/**
	 * @param nums
	 */
	public NumberInfoVO(int[] nums) {
		super(nums);
		init();
	}

	/**
	 * @param str
	 * @throws LottoNumberException
	 */
	public NumberInfoVO(String str) throws LottoNumberException {
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
	public NumberInfoVO(int n1, int n2, int n3, int n4, int n5, int n6, int bn) {
		super(n1, n2, n3, n4, n5, n6, bn);
		init();
	}

	/**
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 * @param n6
	 */
	public NumberInfoVO(int n1, int n2, int n3, int n4, int n5, int n6) {
		super(n1, n2, n3, n4, n5, n6);
		init();
	}

	/**
	 * 
	 */
	private void init() {
		this.round = 0;
		this.date = "0000-00-00";
		this.list = new ArrayList<CountMoneyVO>();
		this.n1Auto = this.n1Manu = this.n1Half = 0;
		this.ac = this.last05 = this.last10 = 0;
	}

	@Override
	public String toString() {
		String str = super.toStringSum(true);
		StringBuilder sb = new StringBuilder();
		String tab = "\t";
		sb.append(Str.fill0(this.round, 4)).append(tab).append(this.date).append(tab).append(str).append(tab)
				.append(this.ac).append(tab).append(this.last05).append(tab).append(this.last10);
		for (CountMoneyVO cm : this.list) {
			sb.append(tab).append(cm.toString());
		}
		sb.append(tab).append(this.n1Auto).append(tab).append(this.n1Manu).append(tab).append(this.n1Half);
		return sb.toString();
	}

	/**
	 * @return
	 */
	public NumberVO getNumberVO() {
		return new NumberVO(this.nums);
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return this.round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public void addCountMoney(CountMoneyVO cm) {
		this.list.add(cm);
	}

	/**
	 * @return the list
	 */
	public List<CountMoneyVO> getCountMoneyList() {
		return this.list;
	}

	/**
	 * @return the n1Auto
	 */
	public int getN1Auto() {
		return this.n1Auto;
	}

	/**
	 * @param n1Auto the n1Auto to set
	 */
	public void setN1Auto(int n1Auto) {
		this.n1Auto = n1Auto;
	}

	/**
	 * @return the n1Manu
	 */
	public int getN1Manu() {
		return this.n1Manu;
	}

	/**
	 * @param n1Manu the n1Manu to set
	 */
	public void setN1Manu(int n1Manu) {
		this.n1Manu = n1Manu;
	}

	/**
	 * @return the n1Half
	 */
	public int getN1Half() {
		return this.n1Half;
	}

	/**
	 * @param n1Half the n1Half to set
	 */
	public void setN1Half(int n1Half) {
		this.n1Half = n1Half;
	}

	/**
	 * @return the ac
	 */
	public int getAc() {
		return this.ac;
	}

	public void calcAc() {
		this.ac = LottoUtil.getAC(this.nums);
	}

	/**
	 * @return the last10
	 */
	public int getLast10() {
		return this.last10;
	}

	/**
	 * @param last10 the last10 to set
	 */
	public void setLast10(int last10) {
		this.last10 = last10;
	}

	/**
	 * @return the last05
	 */
	public int getLast05() {
		return this.last05;
	}

	/**
	 * @param last05 the last05 to set
	 */
	public void setLast05(int last05) {
		this.last05 = last05;
	}

	@Override
	public int compareTo(NumberInfoVO o) {
		int cmp = this.round - o.round;
		return (cmp < 0) ? -1 : (cmp > 0) ? 1 : 0;
	}
}
