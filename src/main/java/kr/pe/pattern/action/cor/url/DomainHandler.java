/**
 * DomainHandler.java
 */
package kr.pe.pattern.action.cor.url;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 7:51:16
 * </pre>
 */
public class DomainHandler extends Handler {

	@Override
	protected void process(String url) {
		int startIndex = url.indexOf("://");
		int lastIndex = url.lastIndexOf(":");

		String fmt = "DOMAIN : {}";
		if (startIndex == -1) {
			if (lastIndex == -1) {
				LogUtil.debugAll(fmt, url);
			} else {
				LogUtil.debugAll(fmt, url.substring(0, lastIndex));
			}
		} else if (startIndex != lastIndex) {
			LogUtil.debugAll(fmt, url.substring(startIndex + 3, lastIndex));
		} else {
			LogUtil.debugAll(fmt, url.substring(startIndex + 3));
		}
	}
}
