/**
 * ButtonFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

import kr.pe.pattern.create.factory.abs.comp.FTextEdit;
import kr.pe.pattern.create.factory.abs.comp.MacFTextEdit;
import kr.pe.pattern.create.factory.abs.comp.WindowFTextEdit;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 5:11:35
 * </pre>
 */
public class FTextEditFactory implements FComponentFactory {

	/**
	 * <pre></pre>
	 * 
	 */
	public FTextEditFactory() {
		super();
	}

	@Override
	public FTextEdit createOperation(String type) {
		FTextEdit textedit = createComponent(type);
//		button.추가설정();
		return textedit;
	}

	@Override
	public FTextEdit createComponent(String type) {

		FTextEdit textedit = null;

		switch (type.toLowerCase()) {
		case "window":
			textedit = new WindowFTextEdit();
			break;
		case "mac":
			textedit = new MacFTextEdit();
			break;
		}
		return textedit;
	}
}
