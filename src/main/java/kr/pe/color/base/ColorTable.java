/**
 * ColorTable.java
 */
package kr.pe.color.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ColorTable {

	private String id;
	private int length;
	private List<ColorItem> colors;

	/**
	 * <pre></pre>
	 */
	public ColorTable() {
		this(null);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 */
	public ColorTable(String id) {
		super();
		this.id = id;
		this.length = 0;
		this.colors = new ArrayList<ColorItem>();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @param colors
	 */
	public ColorTable(String id, List<ColorItem> colors) {
		super();
		this.id = id;
		if (colors == null) {
			this.colors = new ArrayList<ColorItem>();
			this.length = 0;
		} else {
			this.colors = colors;
			this.length = colors.size();
		}
	}

	@XmlAttribute(name = "id") // <table id="..."> 속성 매핑
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the length
	 */
	@XmlAttribute(name = "length") // <table id="..."> 속성 매핑
	public int getLength() {
		return this.length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	@XmlElement(name = "color") // 내부에 반복되는 <color ... /> 태그 매핑
	public List<ColorItem> getColors() {
		return this.colors;
	}

	public void setColors(List<ColorItem> colors) {
		this.colors = colors;
		this.length = colors.size();
	}

	public void addColor(ColorItem color) {
		this.colors.add(color);
		this.length++;
	}

	public ColorItem addColor(String line) {
		ColorItem color = new ColorItem(line);
		this.colors.add(color);
		this.length++;
		return color;
	}
}
