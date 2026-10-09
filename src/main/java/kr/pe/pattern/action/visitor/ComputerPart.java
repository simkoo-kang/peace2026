/**
 * ComputerPart.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 2. 데이터 구조를 이루는 요소 인터페이스
 * 
 * PC  2026. 9. 9. 오후 5:58:40
 * </pre>
 */
public interface ComputerPart {

	public void accept(Visitor visitor); // 방문자를 수용하는 메서드
}
