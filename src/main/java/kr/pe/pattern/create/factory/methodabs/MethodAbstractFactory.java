/**
 * AbstractFactory.java
 */
package kr.pe.pattern.create.factory.methodabs;

import java.util.List;

import kr.pe.pattern.create.factory.abs.comp.FComponent;
import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:11:51
 * </pre>
 */
public class MethodAbstractFactory {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 6:11:51
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		MethodAbstractComponentFactory factory = null;

		// 윈도우 버튼 생성
		factory = new MethodAbstractWindowFactory();
		List<FComponent> list = factory.createOperation();
		for (FComponent comp : list) {
			comp.render();
			LogUtil.debugAll(MethodAbstractFactory.class, comp.getClass().getName());
		}
	}
}
