/**
 * RemoteControl.java
 */
package kr.pe.pattern.action.command;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:30:48
 * </pre>
 */
public class RemoteControl {

	private Command slot; // 명령을 담을 슬롯

	public void setCommand(Command command) {
		this.slot = command;
	}

	public void pressButton() {
		this.slot.execute(); // 리모컨은 뭔지 모르고 그냥 실행만 시킴
	}
}
