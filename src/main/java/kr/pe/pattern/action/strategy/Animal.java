/**
 * Animal.java
 */
package kr.pe.pattern.action.strategy;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:21:27
 * </pre>
 */
public abstract class Animal {

	public void eat() {
		System.out.println("먹는다.");
	}

	public abstract void bark();

	public abstract String getName();
}
