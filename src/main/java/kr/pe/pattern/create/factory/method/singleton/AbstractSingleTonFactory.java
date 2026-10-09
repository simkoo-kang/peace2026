/**
 * AbstractFactory.java
 */
package kr.pe.pattern.create.factory.method.singleton;

import kr.pe.pattern.create.factory.abs.comp.FButton;
import kr.pe.pattern.create.factory.method.AbstractComponentFactory;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:11:51
 * </pre>
 */
public class AbstractSingleTonFactory {

	/**
	 * <pre>
	 * 그러나 모든 확장에 대해 유연하게 대처할수 있는 것은 아니다.
	 *  이번에는 새로운 OS 환경이 아닌 새로운 컴포넌트인 툴팁 을 추가한다고 생각해보자.
	 * 
	 * 그러면 모든 서브 팩토리 클래스마다 툴팁 객체를 생성하는 createToolTip() 메서드를 추가 해야 되니 이때는 오히려 문제점으로 작용하게 된다.
	 * 
	 * PC 2026. 9. 8. 오후 6:11:51
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractComponentFactory factory = null;

		// 윈도우 버튼 생성
		factory = AbstractWindowSingleTonFactory.getInstance();
		FButton windowBtn = createBtn(factory);
		windowBtn.render();

		// 맥 버튼 생성
		factory = AbstractMacSingleTonFactory.getInstance();
		FButton macBtn = createBtn(factory);
		macBtn.render();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 6:13:42
	 * </pre>
	 *
	 * @param factory
	 * @return
	 */
	private static FButton createBtn(AbstractComponentFactory factory) {
		return factory.createButton();
	}

}
