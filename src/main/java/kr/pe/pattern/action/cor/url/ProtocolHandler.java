/**
 * ProtocolHandler.java
 */
package kr.pe.pattern.action.cor.url;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 7:48:14
 * </pre>
 */
public class ProtocolHandler extends Handler {

	@Override
	protected void process(String url) {
		int index = url.indexOf("://");
		if (-1 < index) {
			LogUtil.debugAll(this, "PROTOCOL: {}", url.subSequence(0, index));
		} else {
			LogUtil.debugAll(this, "NO PROTOCOL");
		}
	}
}
