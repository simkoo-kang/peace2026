/**
 * StereoOnCommand.java
 */
package kr.pe.pattern.action.command;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:29:35
 * </pre>
 */
public class StereoOnCommand implements Command {

	private Stereo stereo;

	/**
	 * <pre></pre>
	 * 
	 * @param stereo
	 */
	public StereoOnCommand(Stereo stereo) {
		super();
		this.stereo = stereo;
	}

	@Override
	public void execute() {
		this.stereo.on();
	}

	@Override
	public void undo() {
		this.stereo.off();
	}

}
