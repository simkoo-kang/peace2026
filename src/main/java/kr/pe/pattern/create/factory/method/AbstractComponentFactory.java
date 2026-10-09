/**
 * AbstractComponentFactory.java
 */
package kr.pe.pattern.create.factory.method;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.FCheckBox;
import kr.pe.pattern.create.factory.abs.comp.FTextEdit;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:07:00
 * </pre>
 */
public interface AbstractComponentFactory {

	FButton createButton();

	FCheckBox createCheckBox();

	FTextEdit createTextEdit();
}
