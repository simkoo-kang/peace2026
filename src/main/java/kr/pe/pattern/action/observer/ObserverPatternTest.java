/**
 * ObserverPatternTest.java
 */
package kr.pe.pattern.action.observer;

/**
 * <pre>
 * event가 일어났을 때,(그리고 event가 언제 일어날지 모를 때) 미리 등록된 다른 클래스에 통보해주는 패턴을 구현한 것.
 * ex) event listner
 * 
 * PC  2026. 9. 8. 오전 9:06:49
 * </pre>
 */
public class ObserverPatternTest {

	// Interface
	private interface IButtonListner {
		void clickEvent(String event);
	}

	private static class Button {
//		private String name;
		private IButtonListner buttonListner;

		public Button(String name) {
//			this.name = name;
		}

		public void click(String message) {
			this.buttonListner.clickEvent(message);
		}

		public void addListner(IButtonListner buttonListner) {
			this.buttonListner = buttonListner;
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오전 9:06:49
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Button button = new Button("button");
		button.addListner(new IButtonListner() {
			@Override
			public void clickEvent(String event) {
				System.out.println(event);
			}
		});
		button.click("click 1 send a message.");
		button.click("click 2 send a message.");
		button.click("click 3 send a message.");
	}
}
