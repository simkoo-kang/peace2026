/**
 * GUIFactory.java
 */
package kr.pe.pattern.create.factory.abs.factory;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:45:05
 * </pre>
 */
public class FactoryPattern {

	// Product A 제품군
	static interface AbstractProductA {
	}

	// Product A - 1
	static class ConcreteProductA1 implements AbstractProductA {
	}

	// Product A - 2
	static class ConcreteProductA2 implements AbstractProductA {
	}

	// Product B 제품군
	static interface AbstractProductB {
	}

	// Product B - 1
	static class ConcreteProductB1 implements AbstractProductB {
	}

	// Product B - 2
	static class ConcreteProductB2 implements AbstractProductB {
	}

	static interface AbstractFactory {
		AbstractProductA createProductA();

		AbstractProductB createProductB();
	}

	// Product A1와 B1 제품군을 생산하는 공장군 1
	static class ConcreteFactory1 implements AbstractFactory {
		public AbstractProductA createProductA() {
			return new ConcreteProductA1();
		}

		public AbstractProductB createProductB() {
			return new ConcreteProductB1();
		}
	}

	// Product A2와 B2 제품군을 생산하는 공장군 2
	static class ConcreteFactory2 implements AbstractFactory {
		public AbstractProductA createProductA() {
			return new ConcreteProductA2();
		}

		public AbstractProductB createProductB() {
			return new ConcreteProductB2();
		}
	}

	public static void main(String[] args) {
		AbstractFactory factory = null;

		// 1. 공장군 1을 가동시킨다.
		factory = new ConcreteFactory1();

		// 2. 공장군 1을 통해 제품군 A1를 생성하도록 한다 (클라이언트는 구체적인 구현은 모르고 인터페이스에 의존한다)
		AbstractProductA product_A1 = factory.createProductA();
		System.out.println(product_A1.getClass().getName()); // ConcreteProductA1

		// 3. 공장군 2를 가동시킨다.
		factory = new ConcreteFactory2();

		// 4. 공장군 2를 통해 제품군 A2를 생성하도록 한다 (클라이언트는 구체적인 구현은 모르고 인터페이스에 의존한다)
		AbstractProductA product_A2 = factory.createProductA();
		System.out.println(product_A2.getClass().getName()); // ConcreteProductA2
	}
}
