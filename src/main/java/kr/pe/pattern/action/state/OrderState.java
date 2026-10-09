/**
 * OrderState.java
 */
package kr.pe.pattern.action.state;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 3:00:41
 * </pre>
 */
public interface OrderState {

	public void next(Order order);

	public void printStatus();
}
