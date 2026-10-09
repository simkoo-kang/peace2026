/**
 * COECheckHandlerTest.java
 */
package kr.pe.pattern.action.cor;

import kr.pe.pattern.action.cor.check.AuthenticationMiddleware;
import kr.pe.pattern.action.cor.check.AuthorizeMiddleware;
import kr.pe.pattern.action.cor.check.LimitLoginAttemptMiddleware;
import kr.pe.pattern.action.cor.check.LoggingMiddleware;
import kr.pe.pattern.action.cor.check.Server;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:47:09
 * </pre>
 */
public class CORCheckHandlerTest {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 8:47:09
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. 서버 생성 및 등록
		Server server = new Server();
		server.register("john@naver.com", "123123");
		server.register("kail@google.com", "456456");
		server.register("inpa@tistory.com", "789789");

		// 2. 인증 로직을 처리하는 핸들러 생성
		LimitLoginAttemptMiddleware limitLogin = new LimitLoginAttemptMiddleware();
		AuthorizeMiddleware checkEP = new AuthorizeMiddleware(server);
		AuthenticationMiddleware checkEmail = new AuthenticationMiddleware();
		LoggingMiddleware logging = new LoggingMiddleware();

		// 3. 핸들러 체인
		limitLogin.setNext(checkEP).setNext(checkEmail).setNext(logging);

		String[] emails = new String[] { "gbrain@naver.com", "kail@google.com", "inpa@tistory.com", "kail@google.com",
				"inpa@tistory.com" };
		String[] pwds = new String[] { "123123", "123456", "789012", "456456", "789789" };

		// 4. 클라이언트로부터 로그인 시도
		for (int i = 0; i < emails.length; i++) {
			String email = emails[i];
			String password = pwds[i];
//			LogUtil.debugAll("Email: {}, Password: {}", email, password);

			// 핸들러부터 정수 flag를 받아, 정수값에 따라 루프문 다음 동작을 처리
			int result = limitLogin.check(email, password);

			if (result == -2) {
				throw new RuntimeException("로그인 시도 횟수 초과로 프로그램을 종료합니다");
			} else if (result == -1) {
				break;
			} else if (result == 0) {
				continue;
			}
		}
	}
}
