/**
 * RemoteControl.java
 */
package kr.pe.pattern.action.command.lambda;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:47:27
 * </pre>
 */
public class LambdaRemoteControl {

	private LambdaCommand slot;

	public void setCommand(LambdaCommand command) {
		this.slot = command;
	}

	public void pressButton() {
		this.slot.execute();
	}
}
