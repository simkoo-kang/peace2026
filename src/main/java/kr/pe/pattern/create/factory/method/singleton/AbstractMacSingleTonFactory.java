/**
 * AbstractWindowFactory.java
 */
package kr.pe.pattern.create.factory.method.singleton;

import kr.pe.pattern.create.factory.method.AbstractMacFactory;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:08:59
 * </pre>
 */
public class AbstractMacSingleTonFactory extends AbstractMacFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	private AbstractMacSingleTonFactory() {
		super();
	}

	private static class SingleTonInstanceHolder {
		private static final AbstractMacSingleTonFactory INSTANCE = new AbstractMacSingleTonFactory();
	}

	public static AbstractMacSingleTonFactory getInstance() {
		return SingleTonInstanceHolder.INSTANCE;
	}
}
