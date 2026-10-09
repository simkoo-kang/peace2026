/**
 * Visitor.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 1. 알고리즘(연산)을 정의하는 방문자 인터페이스
 * 
 * PC  2026. 9. 9. 오후 5:57:39
 * </pre>
 */
public interface Visitor {

	public void visit(Cpu cpu);

	public void visit(Gpu gpu);
}
