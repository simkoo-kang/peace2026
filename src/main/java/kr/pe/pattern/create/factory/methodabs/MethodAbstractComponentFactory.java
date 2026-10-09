/**
 * AbstractComponentFactory.java
 */
package kr.pe.pattern.create.factory.methodabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.FCheckBox;
import kr.pe.pattern.create.factory.abs.comp.FComponent;
import kr.pe.pattern.create.factory.abs.comp.FTextEdit;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:07:00
 * </pre>
 */
public interface MethodAbstractComponentFactory {

	FButton createButton();

	FCheckBox createCheckBox();

	FTextEdit createTextEdit();

	default List<FComponent> createOperation() {
		FButton btn = createButton();
		FCheckBox chkbox = createCheckBox();
		FTextEdit txtEdt = createTextEdit();
//		btn.추가세팅();
//		chkbox.추가세팅();
//		txtEdt.추가세팅();

		return new ArrayList<FComponent>(Arrays.asList(btn, chkbox, txtEdt));
	}
}
