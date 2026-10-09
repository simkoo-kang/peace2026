/**
 * ButtonFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.MacFButton;
import kr.pe.pattern.create.factory.abs.comp.WindowFButton;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 5:11:35
 * </pre>
 */
public class FButtonFactory implements FComponentFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	public FButtonFactory() {
		super();
	}

	@Override
	public FButton createOperation(String type) {
		FButton button = createComponent(type);
//		button.추가설정();
		return button;
	}

	@Override
	public FButton createComponent(String type) {

		FButton button = null;

		switch (type.toLowerCase()) {
		case "window":
			button = new WindowFButton();
			break;
		case "mac":
			button = new MacFButton();
			break;
		}
		return button;
	}
}
