/**
 * LimitLoginAttemptMiddleware.java
 */
package kr.pe.pattern.action.cor.check;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:26:04
 * </pre>
 */
public class LimitLoginAttemptMiddleware extends Middleware {
	private int limit = 3; // 로그인 시도 최대 횟수 3회
	private int count = 0;

	@Override
	public int check(String email, String password) {
		int flag = 1;

		if (this.count > this.limit) {
			LogUtil.debugAll(this, "로그인 요청 횟수 제한 !!");
			flag = -2; // Exception 플래그
		} else {
			flag = super.check(email, password);
		}

		this.count++;

		return flag;
	}
}
