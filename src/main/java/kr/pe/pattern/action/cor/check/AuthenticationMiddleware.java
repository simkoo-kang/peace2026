/**
 * AuthenticationMiddleware.java
 */
package kr.pe.pattern.action.cor.check;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:49:58
 * </pre>
 */
public class AuthenticationMiddleware extends Middleware {

	@Override
	public int check(String email, String password) {
		int flag = 1;

		if (email.equals("inpa@tistory.com")) {
			LogUtil.debugAll(this, "Hello, admin!");
			flag = -1; // break 플래그
		} else {
			LogUtil.debugAll(this, "Hello, user!");
			flag = super.check(email, password);
		}

		return flag;
	}
}
