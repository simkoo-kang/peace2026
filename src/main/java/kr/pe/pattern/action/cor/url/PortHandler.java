/**
 * PortHandler.java
 */
package kr.pe.pattern.action.cor.url;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 7:55:08
 * </pre>
 */
public class PortHandler extends Handler {

	@Override
	protected void process(String url) {
		int index = url.lastIndexOf(":");
		if (index != -1) {
			String strPort = url.substring(index + 1);
			try {
				int port = Integer.parseInt((strPort));
				LogUtil.debugAll(this, "PORT : {}", port);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
}
