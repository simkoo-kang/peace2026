/**
 * Airplane.java
 */
package kr.pe.pattern.action.mediator;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 5:33:23
 * </pre>
 */
public abstract class Airplane {

	protected ControlTower controlTower; // 중재자 참조
	protected String name;

	/**
	 * <pre></pre>
	 * 
	 * @param controlTower
	 * @param name
	 */
	public Airplane(ControlTower controlTower, String name) {
		super();
		this.controlTower = controlTower;
		this.name = name;
	}

	public abstract void send(String message);

	public abstract void receive(String message);
}
