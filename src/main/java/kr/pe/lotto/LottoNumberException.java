/**
 * LottoNumberException.java
 */
package kr.pe.lotto;

/**
 * PC 2025. 4. 30.
 */
public class LottoNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1480413625566525986L;

	/**
	 * 
	 */
	public LottoNumberException() {
		super();
	}

	/**
	 * @param message
	 */
	public LottoNumberException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LottoNumberException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LottoNumberException(String message, Throwable cause) {
		super(message, cause);
	}
}
