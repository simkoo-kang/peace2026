/**
 * ComponentAbstractFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

import kr.pe.pattern.create.factory.abs.comp.FComponent;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 4:59:39
 * </pre>
 */
public interface FComponentFactory {
	FComponent createOperation(String type); // 템플릿

	FComponent createComponent(String type); // 팩토리 메서드
}
