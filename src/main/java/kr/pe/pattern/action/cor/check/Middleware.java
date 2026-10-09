/**
 * Middleware.java
 */
package kr.pe.pattern.action.cor.check;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:16:48
 * </pre>
 */
public abstract class Middleware {

	protected Middleware nextMiddleware = null;

	/**
	 * @param nextMiddleware the nextMiddleware to set
	 */
	public Middleware setNext(Middleware nextMiddleware) {
		this.nextMiddleware = nextMiddleware;
		return nextMiddleware;
	}

	/**
	 * <pre>
	 * 핸들러가 요청에 대해 처리하는 함수
	 * 
	 * PC 2026. 9. 8. 오후 8:24:37
	 * </pre>
	 *
	 * @param email
	 * @param password
	 * @return
	 */
	public int check(String email, String password) {
		/**
		 * flag는 클라이언트 실행부에서 while문을 빠져나가기 위한 조건값으로 사용된다. -2: Exception 발생 -1: break 동작
		 * 0: continue 동작 1: 다음 진행
		 */
		int flag = 1;
		if (this.nextMiddleware != null) {
			flag = this.nextMiddleware.check(email, password);
		}
		return flag;
	}
}
