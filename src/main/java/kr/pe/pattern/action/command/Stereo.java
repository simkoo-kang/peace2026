/**
 * Stereo.java
 */
package kr.pe.pattern.action.command;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:26:27
 * </pre>
 */
public class Stereo {

	public void on() {
		LogUtil.debugAll(this, "오디오가 켜졌습니다.");
	}

	public void off() {
		LogUtil.debugAll(this, "오디오가 꺼졌습니다.");
	}
}
