/**
 * BaseElement.java
 */
package kr.pe.game.shooter.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import kr.pe.util.Arr;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public abstract class BaseElement {

	private List<String> cols;

	public BaseElement() {
		super();
		this.cols = new ArrayList<String>();
	}

	@XmlElement(name = "col")
	public List<String> getCols() {
		if (this.cols == null) {
			this.cols = new ArrayList<>();
		}
		return this.cols;
	}

	public void setCols(List<String> cols) {
		this.cols = cols;
	}

	/**
	 * <pre></pre>
	 * 
	 * @return
	 */
	public String[] getColsArray() {
		return Arr.listToStringArray(this.cols);
	}

	/**
	 * <pre>
	 * 편의 메서드: col 데이터를 한 권에 담기
	 * </pre>
	 * 
	 * @param values
	 */
	public void addCols(String... values) {
		// null 체크 로직 추가 (NPE 방어)
		if (this.cols == null) {
			this.cols = new ArrayList<>();
		}
		for (String val : values) {
			this.cols.add(val);
		}
	}
}
