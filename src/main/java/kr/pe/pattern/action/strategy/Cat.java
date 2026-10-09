/**
 * Cat.java
 */
package kr.pe.pattern.action.strategy;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:23:04
 * </pre>
 */
public class Cat extends Animal {

	private final String name = "고양이";

	@Override
	public void bark() {
		System.out.println("야옹");
	}

	@Override
	public String getName() {
		return this.name;
	}

}
