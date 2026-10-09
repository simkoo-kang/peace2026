/**
 * Light.java
 */
package kr.pe.pattern.action.command.lambda;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:45:32
 * </pre>
 */
public class LambdaLight {

	public void on() {
		LogUtil.debugAll(this, "조명이 켜졌습니다.");
	}

	public void off() {
		LogUtil.debugAll(this, "조명이 꺼졌습니다.");
	}
}
