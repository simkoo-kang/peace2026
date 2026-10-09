/**
 * TextEditorMemento.java
 */
package kr.pe.pattern.action.memento;

/**
 * <pre>
 * 1. Memento (스냅숏 객체)
 * 
 * Immutable(불변) 객체로 만들어 외부에서 수정하지 못하게 차단합니다.
 * 
 * PC  2026. 9. 10. 오후 9:26:30
 * </pre>
 */
public final class TextEditorMemento {
	private final String state;

	/**
	 * <pre>
	 * 패키지 내부나 Originator만 접근할 수 있게 생성자를 제한하는 것이 좋습니다.
	 * </pre>
	 * 
	 * @param state
	 */
	public TextEditorMemento(String state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}
}
