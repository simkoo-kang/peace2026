/**
 * AuthorizeMiddleware.java
 */
package kr.pe.pattern.action.cor.check;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:32:16
 * </pre>
 */
public class AuthorizeMiddleware extends Middleware {
	private Server server;

	/**
	 * <pre></pre>
	 * 
	 * @param server
	 */
	public AuthorizeMiddleware(Server server) {
		this.server = server;
	}

	@Override
	public int check(String email, String password) {
		int flag = 1;

		if (!this.server.hasEmail(email)) {
			LogUtil.debugAll(this, "{} This email is not registered!", email);
			flag = 0; // continue 플래그
		} else if (!this.server.isValidPassword(email, password)) {
			LogUtil.debugAll(this, "{} Wrong password!", password);
			flag = 0; // continue 플래그
		} else {
			flag = super.check(email, password);
		}

		return flag;
	}
}
