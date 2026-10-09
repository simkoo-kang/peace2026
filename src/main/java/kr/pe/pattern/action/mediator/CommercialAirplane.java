/**
 * CommercialAirplane.java
 */
package kr.pe.pattern.action.mediator;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 5:39:09
 * </pre>
 */
public class CommercialAirplane extends Airplane {

	/**
	 * <pre></pre>
	 * 
	 * @param controlTower
	 * @param name
	 */
	public CommercialAirplane(ControlTower controlTower, String name) {
		super(controlTower, name);
	}

	@Override
	public void send(String message) {
		LogUtil.debugAll(this, "[{}] 발신: {}", this.name, message);
		this.controlTower.sendMessage(message, this); // 관제탑에 전달
	}

	@Override
	public void receive(String message) {
		LogUtil.debugAll(this, "[{}] 수신 완료: {}", this.name, message);
	}
}
