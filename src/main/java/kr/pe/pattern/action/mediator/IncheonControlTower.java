/**
 * IncheonControlTower.java
 */
package kr.pe.pattern.action.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 5:35:57
 * </pre>
 */
public class IncheonControlTower implements ControlTower {

	private List<Airplane> airplanes = new ArrayList<>();

	@Override
	public void registerAirplane(Airplane airplane) {
		this.airplanes.add(airplane);
	}

	@Override
	public void sendMessage(String message, Airplane originator) {
		// 메시지를 보낸 비행기를 제외한 모든 비행기에 통보
		for (Airplane airplane : this.airplanes) {
			if (airplane != originator) {
				airplane.receive(message);
			}
		}
	}
}
