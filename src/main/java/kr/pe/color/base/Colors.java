/**
 * Colors.java
 */
package kr.pe.color.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
@XmlRootElement(name = "colors") // 최상위 루트 태그 지정
public class Colors {

	private List<ColorTable> tables;

	/**
	 * <pre></pre>
	 */
	public Colors() {
		super();
		this.tables = new ArrayList<ColorTable>();
	}

	@XmlElement(name = "table") // 내부에 반복되는 <table ...> 태그 매핑
	public List<ColorTable> getTables() {
		return this.tables;
	}

	public void setTables(List<ColorTable> tables) {
		this.tables = tables;
	}

	public void addTable(ColorTable ct) {
		this.tables.add(ct);
	}

	public void clear() {
		this.tables.clear();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @return
	 */
	public ColorTable getTable(String id) {
		for (ColorTable ct : this.tables) {
			if (id.equals(ct.getId())) {
				return ct;
			}
		}
		return null;
	}
}
