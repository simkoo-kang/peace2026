/**
 * StageVo.java
 */
package kr.pe.game.shooter.vos;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public class ReadyVo extends ElementLengthVo {

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 */
	public ReadyVo(int length) {
		super(length);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param length
	 * @param cols
	 */
	public ReadyVo(int length, String... cols) {
		super(length, cols);
	}
}
