/**
 * LightOnCommand.java
 */
package kr.pe.pattern.action.command;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:27:36
 * </pre>
 */
public class LightOnCommand implements Command {

	private Light light;

	/**
	 * <pre></pre>
	 * 
	 * @param light
	 */
	public LightOnCommand(Light light) {
		super();
		this.light = light;
	}

	@Override
	public void execute() {
		this.light.on();
	}

	@Override
	public void undo() {
		this.light.off();
	}
}
