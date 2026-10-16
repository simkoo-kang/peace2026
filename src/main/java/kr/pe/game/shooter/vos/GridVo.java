/**
 * GridVo.java
 */
package kr.pe.game.shooter.vos;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public class GridVo {

	private int rows, cols;
	private List<RowVo> rowVos;

	/**
	 * <pre></pre>
	 * 
	 * @param rows
	 * @param cols
	 */
	public GridVo(int rows, int cols) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.rowVos = new ArrayList<RowVo>();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param rows
	 * @param cols
	 * @param rowVos
	 */
	public GridVo(int rows, int cols, List<RowVo> rowVos) {
		this(rows, cols);
		this.rowVos = rowVos;
	}

	/**
	 * <pre></pre>
	 * 
	 * @return
	 */
	public String[][] getGrid() {
		String[][] grid = new String[this.rows][this.cols];
		for (int r = 0; r < this.rows; r++) {
			RowVo vo = this.rowVos.get(r);
			System.arraycopy(vo.cols, 0, grid[r], 0, this.cols);
		}
		return grid;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * @return the rowVos
	 */
	public List<RowVo> getRowVos() {
		return this.rowVos;
	}

	/**
	 * @param rowVos the rowVos to set
	 */
	public void setRowVos(List<RowVo> rowVos) {
		if (0 < this.rowVos.size()) {
			this.rowVos.clear();
		}
		for (RowVo vo : rowVos) {
			this.rowVos.add(vo);
		}
	}
}
