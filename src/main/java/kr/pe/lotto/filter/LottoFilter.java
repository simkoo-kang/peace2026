/**
 * 
 */
package kr.pe.lotto.filter;

import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;

/**
 * 
 */
public interface LottoFilter {

	public boolean filter(NumberVO ivo);

	public boolean filter(NumberInfoVO ivo);

	public boolean filter(int[] nums);

	public String getName();

	public void setName(String name);
}