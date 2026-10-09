/**
 * LoggingMiddleware.java
 */
package kr.pe.pattern.action.cor.check;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:44:50
 * </pre>
 */
public class LoggingMiddleware extends Middleware {

	@Override
	public int check(String email, String password) {
		LogUtil.debugAll(this, "요청을 로깅합니다.");
		return -1; // break 플래그
	}

}
