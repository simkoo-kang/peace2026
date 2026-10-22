/**
 * ColorItem.java
 */
package kr.pe.color.base;

import jakarta.xml.bind.annotation.XmlAttribute;
import kr.pe.util.iter.Iter;
import kr.pe.util.iter.StrIter;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ColorItem {

	private String name;
	private String hex;
	private String rgb;

	/**
	 * <pre></pre>
	 */
	public ColorItem() {
		super();
		this.name = this.hex = this.rgb = null;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param hex
	 * @param rgb
	 */
	public ColorItem(String name, String hex, String rgb) {
		super();
		this.name = name;
		this.hex = hex;
		this.rgb = rgb;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param line gray82 #D1D1D1 209,209,209
	 */
	public ColorItem(String line) {
		super();
		this.setLine(line);
	}

	@Override
	public String toString() {
		if (this.name == null || this.hex == null || this.rgb == null) {
			return "";
		}
		return this.name + " " + this.hex + " " + this.rgb;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param line
	 */
	public void setLine(String line) {
		Iter it = new StrIter("[^\\s]+");
		it.find(line);
		if (2 < it.size()) {
			this.name = it.next();
			this.hex = it.next();
			this.rgb = it.next();
		}
	}

	@XmlAttribute(name = "name") // <color name="..."> 속성 매핑
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "hex") // <color hex="..."> 속성 매핑
	public String getHex() {
		return this.hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	@XmlAttribute(name = "rgb") // <color rgb="..."> 속성 매핑
	public String getRgb() {
		return this.rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
}
