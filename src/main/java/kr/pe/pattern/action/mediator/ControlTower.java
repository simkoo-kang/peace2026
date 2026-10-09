/**
 * ControlTower.java
 */
package kr.pe.pattern.action.mediator;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 5:31:50
 * </pre>
 */
public interface ControlTower {

	public void registerAirplane(Airplane airplane);

	public void sendMessage(String message, Airplane originator);
}
