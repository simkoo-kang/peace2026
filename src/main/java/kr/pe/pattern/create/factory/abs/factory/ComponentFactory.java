/**
 * ComponentFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.abs.comp.FCheckBox;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 5:58:29
 * </pre>
 */
public class ComponentFactory {

	/**
	 * <pre>
	 * 팩토리 메서로 구현해본 코드는 실행 자체는 문제가 없어 보이지만,
	 *  만일 기능을 확장할 필요가 있을 때 문제가 생기게 된다.
	 * 
	 * 예를 들어 OS 종류에 Linux를 새로 추가한다고 생각해보자.
	 *  그러면 각 메서드마다 있는 분기문 로직을 일일히 수정하여야 하는데,
	 *  그러면 OCP 원칙Visit Website에 위배되는 꼴이 된다.
	 * 
	 * PC 2026. 9. 8. 오후 5:58:29
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		FComponentFactory factory = null;
		FButton btn = null;
		FCheckBox chkBox = null;

		factory = new FButtonFactory();
		btn = (FButton) factory.createOperation("window");
		btn.render();

		btn = (FButton) factory.createOperation("mac");
		btn.render();

		factory = new FCheckBoxFactory();
		chkBox = (FCheckBox) factory.createOperation("window");
		chkBox.render();

		chkBox = (FCheckBox) factory.createOperation("mac");
		chkBox.render();
	}

}
