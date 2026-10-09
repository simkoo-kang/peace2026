/**
 * PriceVisitor.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 로직 1: 일반 가격 계산기
 * 
 * PC  2026. 9. 9. 오후 6:03:35
 * </pre>
 */
public class PriceVisitor implements Visitor {

	private int total = 0;

	@Override
	public void visit(Cpu cpu) {
		this.total += cpu.getPrice();
	}

	@Override
	public void visit(Gpu gpu) {
		this.total += gpu.getPrice();
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return this.total;
	}
}
