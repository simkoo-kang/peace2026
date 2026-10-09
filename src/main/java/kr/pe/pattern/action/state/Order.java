/**
 * Order.java
 */
package kr.pe.pattern.action.state;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 3:02:24
 * </pre>
 */
public class Order {

	private OrderState state;

	/**
	 * <pre></pre>
	 * 
	 */
	public Order() {
		super();
		this.state = new PaidState(); // 초기 상태는 결제 완료
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void nextStep() {
		this.state.next(this);
	}

	public void printStatus() {
		this.state.printStatus();
	}
}
