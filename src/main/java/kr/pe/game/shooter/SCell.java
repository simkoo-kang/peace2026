/**
 * SCell.java
 */
package kr.pe.game.shooter;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 13.
 */
public class SCell {

	private String color;
	private int r, c, group;

	/**
	 * <pre></pre>
	 * 
	 * @param color
	 * @param r
	 * @param c
	 */
	public SCell(String color, int r, int c) {
		this(color, r, c, 0);
	}

	/**
	 * <pre></pre>
	 * 
	 * @param color
	 * @param r
	 * @param c
	 * @param group
	 */
	public SCell(String color, int r, int c, int group) {
		super();
		this.color = color;
		this.r = r;
		this.c = c;
		this.group = group;
	}

	@Override
	public String toString() {
		return "SCell [color=" + this.color + ", r=" + this.r + ", c=" + this.c + "]";
	}

	/**
	 * <pre>
	 * clone cell
	 * </pre>
	 * 
	 * @return
	 */
	public SCell copyMe() {
		return new SCell(this.color, this.r, this.c, this.group);
	}

	/**
	 * <pre>
	 * 현재 cell이 벽[#]인 지 여부
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isWall() {
		return "#".equals(this.color);
	}

	/**
	 * <pre>
	 * if empty cell
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return ".".equals(this.color);
	}

	/**
	 * <pre>
	 * 벽이나 빈 칸이 아니면 true
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isAble() {
		return !("#.?".contains(this.color));
	}

	/**
	 * <pre>
	 * check if same group
	 * </pre>
	 * 
	 * @param group
	 * @return
	 */
	public boolean isGroup(int group) {
		return (group != 0 && this.group == group);
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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
	 * @return the c
	 */
	public int getC() {
		return this.c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(int c) {
		this.c = c;
	}

	/**
	 * @return the group
	 */
	public int getGroup() {
		return this.group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(int group) {
		this.group = group;
	}
}
