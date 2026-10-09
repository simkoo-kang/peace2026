/**
 * Cpu.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 6:00:30
 * </pre>
 */
public class Cpu implements ComputerPart {

	private final int price = 300000;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this); // 더블 디스패치의 핵심: 자신을 방문자에게 넘김
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return this.price;
	}
}
