/**
 * Light.java
 */
package kr.pe.pattern.action.command;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:25:03
 * </pre>
 */
public class Light {

	public void on() {
		LogUtil.debugAll(this, "조명이 켜졌습니다.");
	}

	public void off() {
		LogUtil.debugAll(this, "조명이 꺼졌습니다.");
	}
}
