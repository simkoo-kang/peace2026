/**
 * AbstractWindowFactory.java
 */
package kr.pe.pattern.create.factory.method.singleton;

import kr.pe.pattern.create.factory.method.AbstractWindowFactory;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:08:59
 * </pre>
 */
public class AbstractWindowSingleTonFactory extends AbstractWindowFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	private AbstractWindowSingleTonFactory() {
		super();
	}

	private static class SingleTonInstanceHolder {
		private static final AbstractWindowSingleTonFactory INSTANCE = new AbstractWindowSingleTonFactory();
	}

	public static AbstractWindowSingleTonFactory getInstance() {
		return SingleTonInstanceHolder.INSTANCE;
	}
}
