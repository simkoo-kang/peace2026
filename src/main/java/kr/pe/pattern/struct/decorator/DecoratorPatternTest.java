/**
 * DecoratorPatternTest.java
 */
package kr.pe.pattern.struct.decorator;

import kr.pe.util.Str;

/**
 * <pre>
 * 메서드 호출의 반환값에 변화를 주기 위해 중간에 장식자를 두는 패턴
 * 
 * 기존 뼈대(클래스)는 유지하되, 이후 필요한 형태를 꾸밀 때 사용.
 * extends, implement 을 통해 확장하는 형태로 활용.
 * 
 * 예시로는, 기본 자동차(Audi)의 옵션이 추가되면서 장식(Decorator)을 하는 패턴을 보여주겠습니다.
 *  아우디 자동차에 기본적인 기능 외 여러 옵션을 추가할 겁니다.
 * 
 * PC  2026. 9. 8. 오전 7:46:41
 * </pre>
 */
public class DecoratorPatternTest {

	private static interface ICar {
		int getPrice();

		void showPrice();
	}

	private static class Audi implements ICar {

		@Override
		public int getPrice() {
			return 50000;
		}

		@Override
		public void showPrice() {
			System.out.println("Audi 기본 모델 가격은 " + Str.numberFormat(getPrice()) + "원 입니다.");
		}
	}

	/**
	 * <pre>
	 * AudiDecorator, NavigationSystemDecorator 데코테리어 클래스
	 * 
	 * PC  2026. 9. 8. 오전 7:51:46
	 * </pre>
	 */
	private static class AudiDecorator implements ICar {
		protected ICar audi;
		protected String modelName;
		protected int modelPrice;

		public AudiDecorator(ICar audi, String modelName, int modelPrice) {
			this.audi = audi;
			this.modelName = modelName;
			this.modelPrice = modelPrice;
		}

		@Override
		public int getPrice() {
			return this.audi.getPrice() + this.modelPrice;
		}

		@Override
		public void showPrice() {
			System.out.println("Audi " + this.modelName + " Price is " + Str.numberFormat(getPrice()) + "원 입니다.");
		}
	}

	private static class NavigationSystemDecorator extends AudiDecorator {

		/**
		 * <pre></pre>
		 * 
		 * @param audi
		 * @param modelName
		 * @param modelPrice
		 */
		public NavigationSystemDecorator(ICar audi, String modelName, int modelPrice) {
			super(audi, modelName, modelPrice);
		}

		@Override
		public int getPrice() {
			return this.audi.getPrice() + this.modelPrice;
		}

		@Override
		public void showPrice() {
			System.out.println("Audi " + this.modelName + " Price is " + Str.numberFormat(getPrice())
					+ "원 (Navigation System Included)");
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오전 7:46:41
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ICar basicAudi = new Audi();
		basicAudi.showPrice();

		// 보통 A 클래스 = new B (new C(), "A6", 2000); 이런 형태로 감싸주는 구조이다.
		ICar audiWithNavigationCar = new NavigationSystemDecorator(basicAudi, "A6", 2000);
		audiWithNavigationCar.showPrice();
	}

}
