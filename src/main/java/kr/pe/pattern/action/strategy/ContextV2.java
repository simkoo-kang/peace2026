/**
 * ContextV2.java
 */
package kr.pe.pattern.action.strategy;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:19:24
 * </pre>
 */
public class ContextV2 {

	/**
	 * <pre></pre>
	 * 
	 */
	public ContextV2() {
		super();
	}

	/**
	 * <pre>
	 * 전략객체를 '파라미터'로 전달 받는 방식 : 가장 대표적인 방식
	 * 
	 * PC 2026. 9. 8. 오전 10:19:53
	 * </pre>
	 *
	 * @param strategy
	 */
	public void execute(Strategy strategy) { // 피라미타로 strategy 전달
		long startTime = System.currentTimeMillis();
		// 비즈니스 로직 실행
		strategy.call(); // 위임
		// 비즈니스 로직 종료
		long endTime = System.currentTimeMillis();
		long resultTime = endTime - startTime;
		System.out.println("resultTime=" + resultTime);
	}
}
