/**
 * ColorVO.java
 */
package kr.pe.color;

import kr.pe.util.iter.Iter;
import kr.pe.util.iter.NumIter;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ColorVO implements Comparable<ColorVO> {

	private String name, hex, rgb;
	private int r, g, b;

	private int sortField;

	/**
	 * <pre></pre>
	 */
	public ColorVO() {
		super();
		this.name = this.hex = this.rgb = null;
		this.r = this.g = this.b = -1;
		this.sortField = 0;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param hex
	 * @param rgb
	 */
	public ColorVO(String name, String hex, String rgb) {
		this();
		this.name = name;
		this.hex = hex;
		this.rgb = rgb;
		this.setRgb(rgb);
	}

	@Override
	public String toString() {
		return this.name + " " + this.hex + " " + this.rgb;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the hex
	 */
	public String getHex() {
		return this.hex;
	}

	/**
	 * @param hex the hex to set
	 */
	public void setHex(String hex) {
		this.hex = hex;
	}

	/**
	 * @return the rgb
	 */
	public String getRgb() {
		return this.rgb;
	}

	/**
	 * @param rgb the rgb to set
	 */
	public void setRgb(String rgb) {
		this.rgb = rgb;
		Iter it = new NumIter();
		for (int i = 0; i < it.size(); i++) {
			int v = it.nextInt();
			switch (i) {
			case 0:
				this.r = v;
				break;
			case 1:
				this.g = v;
				break;
			default:
				this.b = v;
			}
		}
	}

	/**
	 * @return the r
	 */
	public int getR() {
		return this.r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(int r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public int getG() {
		return this.g;
	}

	/**
	 * @param g the g to set
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public int getB() {
		return this.b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(int b) {
		this.b = b;
	}

	/**
	 * @return the sortField
	 */
	public int getSortField() {
		return this.sortField;
	}

	/**
	 * @param sortField the sortField to set
	 */
	public void setSortField(int sortField) {
		this.sortField = sortField;
	}

	@Override
	public int compareTo(ColorVO o) {
		int cmp;
		switch (this.sortField) {
		case 0:
			cmp = this.name.compareTo(o.name);
			break;
		case 1:
			cmp = this.hex.compareTo(o.hex);
			break;
		default:
			cmp = this.rgb.compareTo(o.rgb);
		}
		return cmp;
	}
}
