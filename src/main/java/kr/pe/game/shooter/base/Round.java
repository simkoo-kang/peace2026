/**
 * Round.java
 */
package kr.pe.game.shooter.base;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Round {

	private String id;
	private Grid grid;
	private Stages stages;
	private Readys readys;

	/**
	 * <pre>
	 * 기본 Jaxb 용
	 * </pre>
	 */
	public Round() {
		super();
	}

	/**
	 * <pre>
	 * 새로 추가 용
	 * </pre>
	 * 
	 * @param id
	 * @param grid
	 * @param stages
	 * @param readys
	 */
	public Round(String id, Grid grid, Stages stages, Readys readys) {
		this.id = id;
		this.grid = grid;
		this.stages = stages;
		this.readys = readys;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "grid")
	public Grid getGrid() {
		return this.grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	@XmlElement(name = "stages")
	public Stages getStages() {
		return this.stages;
	}

	public void setStages(Stages stages) {
		this.stages = stages;
	}

	// XML 원본에 닫는 태그가 </readts> 오타로 되어 있어도 태그명인 <readys> 기준으로 매핑됩니다.
	@XmlElement(name = "readys")
	public Readys getReadys() {
		return this.readys;
	}

	public void setReadys(Readys readys) {
		this.readys = readys;
	}
}
