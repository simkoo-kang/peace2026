/**
 * Handler.java
 */
package kr.pe.pattern.action.cor.url;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 7:42:24
 * </pre>
 */
public abstract class Handler {

	// 다음 체인으로 연결될 핸들러
	protected Handler nextHandler = null;

	/**
	 * <pre>
	 * 다음 연결을 위한 핸들러
	 * 
	 * PC 2026. 9. 8. 오후 7:44:30
	 * </pre>
	 *
	 * @param handler
	 * @return
	 */
	public Handler setNext(Handler handler) {
		this.nextHandler = handler;
		return handler;
	}

	// 자식 핸들러에게 수체화하는 추상 메서드
	protected abstract void process(String url);

	/**
	 * <pre>
	 * 핸들러가 요청에 대해 처리하는 메서드
	 * 
	 * PC 2026. 9. 8. 오후 7:46:12
	 * </pre>
	 *
	 * @param url
	 */
	public void run(String url) {
		process(url);

		// 만일 핸들러가 연결된게 있다면 다음 핸들러로 책임을 넘긴다.
		if (this.nextHandler != null) {
			this.nextHandler.run(url);
		}
	}
}
