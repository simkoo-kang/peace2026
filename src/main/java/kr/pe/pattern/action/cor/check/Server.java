/**
 * Server.java
 */
package kr.pe.pattern.action.cor.check;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 8:37:03
 * </pre>
 */
public class Server {

	private Map<String, String> map = new HashMap<>();

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 8:39:59
	 * </pre>
	 *
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean isValidPassword(String email, String password) {
		String pwd = this.map.get(email);
		if (pwd == null || pwd.trim().equals("")) {
			return false;
		}
		return pwd.equals(password);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 8:40:58
	 * </pre>
	 *
	 * @param email
	 * @return
	 */
	public boolean hasEmail(String email) {
		return this.map.containsKey(email);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 8:41:49
	 * </pre>
	 *
	 * @param email
	 * @param password
	 */
	public void register(String email, String password) {
		this.map.put(email, password);
	}
}
