/**
 * DeliveredState.java
 */
package kr.pe.pattern.action.state;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 3:08:38
 * </pre>
 */
public class DeliveredState implements OrderState {

	@Override
	public void next(Order order) {
		LogUtil.debugAll(this, "이미 배송이 완료된 주문입니다.");
	}

	@Override
	public void printStatus() {
		LogUtil.debugAll(this, "현재 상태: 배송 완료");
	}
}
