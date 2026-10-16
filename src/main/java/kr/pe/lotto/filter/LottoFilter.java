/**
 * 
 */
package kr.pe.lotto.filter;

import java.util.List;

import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;

/**
 * 
 */
public interface LottoFilter {

	public boolean filter(NumberVO ivo);

	public boolean filter(NumberInfoVO ivo);

	public boolean filter(int[] nums);

	public boolean filter(List<Integer> list);

	public String getName();

	public void setName(String name);
}