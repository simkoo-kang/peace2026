/**
 * Shooter.java
 */
package kr.pe.game.shooter;

/**
 * <pre>
 * 
 * PC  2026. 10. 8. 오전 11:16:35
 * </pre>
 */
public class Shooter {
	private String color;
	private int bullets;

	public Shooter(String color) {
		this(color, 2);
	}

	public Shooter(String color, int bullets) {
		this.color = color;
		this.bullets = bullets;
	}

	@Override
	public String toString() {
		return "Shooter [color=" + this.color + ", bullets=" + this.bullets + "]";
	}

	/**
	 * <pre>
	 * copy me
	 * </pre>
	 * 
	 * @return
	 */
	public Shooter copyMe() {
		return new Shooter(this.color, this.bullets);
	}

	public boolean isColor(String color) {
		return color.equals(this.color);
	}

	public boolean killing() {
		if (this.bullets < 1) {
			return false;
		}
		this.bullets--;
		return true;
	}

	public boolean hasPower() {
		return 0 < this.bullets;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * @return the bullets
	 */
	public int getBullets() {
		return this.bullets;
	}
}
