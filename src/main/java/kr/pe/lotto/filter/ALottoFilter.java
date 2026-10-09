/**
 * 
 */
package kr.pe.lotto.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;

/**
 * 
 */
public abstract class ALottoFilter implements LottoFilter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String name;

	/**
	 * @param name
	 */
	public ALottoFilter(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean filter(NumberVO vo) {
		return filter(vo.getNums());
	}

	@Override
	public boolean filter(NumberInfoVO ivo) {
		return filter(ivo.getNums6());
	}
}
