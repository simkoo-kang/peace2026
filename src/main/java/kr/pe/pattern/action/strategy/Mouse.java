/**
 * Mouse.java
 */
package kr.pe.pattern.action.strategy;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:24:19
 * </pre>
 */
public class Mouse extends Animal {

	private final String name = "쥐";

	@Override
	public void bark() {
		System.out.println("찍찍");
	}

	@Override
	public String getName() {
		return this.name;
	}
}
