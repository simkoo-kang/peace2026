/**
 * HistoryCareTaker.java
 */
package kr.pe.pattern.action.memento;

import java.util.Stack;

/**
 * <pre>
 * 3. Caretaker (히스토리를 관리하는 관리자 객체)
 * 
 * PC  2026. 9. 10. 오후 9:32:23
 * </pre>
 */
public class HistoryCareTaker {

	// 이전 상태들을 스택으로 관리 (LIFO 구조로 Undo 구현)
	private final Stack<TextEditorMemento> history = new Stack<>();

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 9:33:05
	 * </pre>
	 *
	 * @param memento
	 */
	public void push(TextEditorMemento memento) {
		this.history.push(memento);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 9:33:11
	 * </pre>
	 *
	 * @return
	 */
	public TextEditorMemento pop() {
		if (!this.history.isEmpty()) {
			return this.history.pop();
		}
		return null;
	}
}
