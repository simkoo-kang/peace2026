/**
 * Grid.java
 */
package kr.pe.game.shooter.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Grid {

	private int rows;
	private int cols;
	private List<Row> rowList;

	/**
	 * <pre>
	 * 기본 Jaxb 용
	 * </pre>
	 */
	public Grid() {
		super();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param rows
	 * @param cols
	 */
	public Grid(int rows, int cols) {
		this(rows, cols, new ArrayList<Row>());
	}

	/**
	 * <pre>
	 * 새로 추가 용
	 * </pre>
	 * 
	 * @param rows
	 * @param cols
	 * @param rowList
	 */
	public Grid(int rows, int cols, List<Row> rowList) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.rowList = rowList;
	}

	/**
	 * <pre>
	 * 유효한 셀 가져오기 (#.) 제외
	 * </pre>
	 * 
	 * @return
	 */
	public List<String> getColors() {
		List<String> list = new ArrayList<String>();
		if (this.rowList != null && 0 < this.rowList.size()) {
			for (int r = 0; r < this.rows; r++) {
				Row row = this.rowList.get(r);
				for (String c : row.getCols()) {
					if (!"#.".contains(c)) {
						list.add(c);
					}
				}
			}
		}
		return list;
	}

	/**
	 * <pre></pre>
	 * 
	 * @return
	 */
	public String[][] getGrid() {
		String[][] grid = new String[this.rows][this.cols];
		for (int r = 0; r < this.rows; r++) {
			Row row = this.rowList.get(r);
			System.arraycopy(row.getColsArray(), 0, grid[r], 0, this.cols);
		}
		return grid;
	}

	@XmlAttribute(name = "rows")
	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@XmlAttribute(name = "cols")
	public int getCols() {
		return this.cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	@XmlElement(name = "row")
	public List<Row> getRowList() {
		return this.rowList;
	}

	public void setRowList(List<Row> rowList) {
		this.rowList = rowList;
	}

	/**
	 * <pre>
	 * 편의 메서드: 격자에 한 줄(Row) 추가
	 * </pre>
	 * 
	 * @param row
	 */
	public void addRow(Row row) {
		this.rowList.add(row);
	}
}
