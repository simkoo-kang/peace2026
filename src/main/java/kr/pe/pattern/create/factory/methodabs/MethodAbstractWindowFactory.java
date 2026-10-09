/**
 * AbstractWindowFactory.java
 */
package kr.pe.pattern.create.factory.methodabs;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.FCheckBox;
import kr.pe.pattern.create.factory.abs.comp.FTextEdit;
import kr.pe.pattern.create.factory.abs.comp.WindowFButton;
import kr.pe.pattern.create.factory.abs.comp.WindowFCheckBox;
import kr.pe.pattern.create.factory.abs.comp.WindowFTextEdit;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:08:59
 * </pre>
 */
public class MethodAbstractWindowFactory implements MethodAbstractComponentFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	public MethodAbstractWindowFactory() {
		super();
	}

	@Override
	public FButton createButton() {
		return new WindowFButton();
	}

	@Override
	public FCheckBox createCheckBox() {
		return new WindowFCheckBox();
	}

	@Override
	public FTextEdit createTextEdit() {
		return new WindowFTextEdit();
	}

}
