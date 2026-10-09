/**
 * AbstractWindowFactory.java
 */
package kr.pe.pattern.create.factory.method;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.FCheckBox;
import kr.pe.pattern.create.factory.abs.comp.FTextEdit;
import kr.pe.pattern.create.factory.abs.comp.MacFButton;
import kr.pe.pattern.create.factory.abs.comp.MacFCheckBox;
import kr.pe.pattern.create.factory.abs.comp.MacFTextEdit;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:08:59
 * </pre>
 */
public class AbstractMacFactory implements AbstractComponentFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	public AbstractMacFactory() {
		super();
	}

	@Override
	public FButton createButton() {
		return new MacFButton();
	}

	@Override
	public FCheckBox createCheckBox() {
		return new MacFCheckBox();
	}

	@Override
	public FTextEdit createTextEdit() {
		return new MacFTextEdit();
	}

}
