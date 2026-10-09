/**
 * TextEditor.java
 */
package kr.pe.pattern.action.memento;

/**
 * <pre>
 * 2. Originator (상태를 가진 원조자 객체)
 * 
 * PC  2026. 9. 10. 오후 9:28:54
 * </pre>
 */
public class TextEditor {

	private String text;

	/**
	 * <pre>
	 * 현재 상태를 스냅숏(Memento)으로 만듦
	 * 
	 * PC 2026. 9. 10. 오후 9:31:00
	 * </pre>
	 *
	 * @return
	 */
	public TextEditorMemento save() {
		return new TextEditorMemento(this.text);
	}

	/**
	 * <pre>
	 * 메멘토를 받아 이전 상태로 복구함
	 * 
	 * PC 2026. 9. 10. 오후 9:31:17
	 * </pre>
	 *
	 * @param memento
	 */
	public void restore(TextEditorMemento memento) {
		this.text = memento.getState();
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return this.text;
	}

	/**
	 * @param text the content to set
	 */
	public void setContent(String text) {
		this.text = text;
	}
}
