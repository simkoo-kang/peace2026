/**
 * Gpu.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 6:02:07
 * </pre>
 */
public class Gpu implements ComputerPart {

	private final int price = 800000;

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return this.price;
	}
}
