/**
 * BaseLengthElement.java
 */
package kr.pe.game.shooter.base;

import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public abstract class BaseLengthElement extends BaseElement {

	private int length;

	@XmlAttribute(name = "length")
	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
