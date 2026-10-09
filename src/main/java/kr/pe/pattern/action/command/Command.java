/**
 * Command.java
 */
package kr.pe.pattern.action.command;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 2:23:57
 * </pre>
 */
public interface Command {
	void execute();

	void undo(); // (옵션) 실행 취소 기능도 쉽게 구현 가능
}
