/**
 * PaidState.java
 */
package kr.pe.pattern.action.state;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오후 3:05:09
 * </pre>
 */
public class PaidState implements OrderState {

	@Override
	public void next(Order order) {
		LogUtil.debugAll(this, "상품 배송을 시작합니다.");
		order.setState(new ShippingState()); // 다음 상태로 전환
	}

	@Override
	public void printStatus() {
		LogUtil.debugAll(this, "현재 상태: 결제 완료");
	}
}
