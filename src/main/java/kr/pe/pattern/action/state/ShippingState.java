/**
 * ShippingState.java
 */
package kr.pe.pattern.action.state;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 3:07:16
 * </pre>
 */
public class ShippingState implements OrderState {

	@Override
	public void next(Order order) {
		LogUtil.debugAll(this, "상품 배송이 완료되었습니다.");
		order.setState(new DeliveredState()); // 다음 상태로 전환
	}

	@Override
	public void printStatus() {
		LogUtil.debugAll(this, "현재 상태: 배송 중");
	}
}
