/**
 * BlackFridayVisitor.java
 */
package kr.pe.pattern.action.visitor;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 6:05:22
 * </pre>
 */
public class BlackFridayVisitor implements Visitor {

	private int total = 0;

	@Override
	public void visit(Cpu cpu) {
		this.total += (int) (cpu.getPrice() * 0.8);
	}

	@Override
	public void visit(Gpu gpu) {
		this.total += (int) (gpu.getPrice() * 0.8);
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return this.total;
	}
}
