/**
 * StrategyPatternTest.java
 */
package kr.pe.pattern.action.strategy;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:26:07
 * </pre>
 */
public class StrategyPatternTest {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오전 10:26:07
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// Animal(Mouse, Cat) , DoorMan 객체를 App context 에 주입!
		Animal m = new Mouse(); // Mouse m = new Mouse(); 와 같은 의미
		Animal cat = new Cat(); // Cat cat = new Cat(); 와 같은 의미

		DoorMan doorMan = new DoorMan();
		doorMan.chaseOut(m);
		doorMan.chaseOut(cat);
	}
}
