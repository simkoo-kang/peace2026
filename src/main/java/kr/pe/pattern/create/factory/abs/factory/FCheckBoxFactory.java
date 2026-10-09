/**
 * ButtonFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

import kr.pe.pattern.create.factory.abs.comp.FCheckBox;
import kr.pe.pattern.create.factory.abs.comp.MacFCheckBox;
import kr.pe.pattern.create.factory.abs.comp.WindowFCheckBox;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 5:11:35
 * </pre>
 */
public class FCheckBoxFactory implements FComponentFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	public FCheckBoxFactory() {
		super();
	}

	@Override
	public FCheckBox createOperation(String type) {
		FCheckBox checkbox = createComponent(type);
//		button.추가설정();
		return checkbox;
	}

	@Override
	public FCheckBox createComponent(String type) {

		FCheckBox checkbox = null;

		switch (type.toLowerCase()) {
		case "window":
			checkbox = new WindowFCheckBox();
			break;
		case "mac":
			checkbox = new MacFCheckBox();
			break;
		}
		return checkbox;
	}
}
