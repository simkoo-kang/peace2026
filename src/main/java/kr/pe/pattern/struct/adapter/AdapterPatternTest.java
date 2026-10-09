/**
 * AdapterPatternTest.java
 */
package kr.pe.pattern.struct.adapter;

/**
 * <pre>
 * 호출당하는 쪽의 메소드를 호출하는 쪽의 코드에 대응하도록 중간에 변환기를 통해 호출하는 패턴
 * 
 * 호환성이 없는 기존 클래스의 인터페이스를 변환해서 재사용하기 위해 필요.
 * 
 * ex) JDBC, JRE
 * 서로 다른 두 인터페이스 사이에 통신이 가능하게 하는 것 합성, 즉 객체의 속성으로 만들어서 참조하는 디자인 패턴
 * 
 * ex. 220V 전자제품을 110V 전자제품으로 사용할 수 있도록 하는 어댑터를 구현하는 예시
 * 
 * PC  2026. 9. 7. 오후 10:00:45
 * </pre>
 */
public class AdapterPatternTest {

	private static interface Electronic110V {
		void powerOn();
	}

	private static interface Electronic220V extends Electronic110V {
		void connect();
	}

	private static class AdaptorSocket implements Electronic110V {
		private Electronic220V electronic220V;

		public AdaptorSocket(Electronic220V electronic220V) {
			this.electronic220V = electronic220V;
		}

		@Override
		public void powerOn() {
			this.electronic220V.connect();
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오후 10:00:45
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Electronic220V electronix220V = new Electronic220V() {
			@Override
			public void connect() {
				System.out.println("220V 전자제품 연결");
			}

			@Override
			public void powerOn() {
				System.out.println("220V 전자제품 ON");
			}
		};

		Electronic110V adapter = new AdaptorSocket(electronix220V);
		adapter.powerOn();
	}

}
