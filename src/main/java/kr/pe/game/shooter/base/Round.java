/**
 * Round.java
 */
package kr.pe.game.shooter.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import kr.pe.util.Coll;
import kr.pe.util.LogUtil;
import kr.pe.util.Str;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
public class Round {

	private String id;
	private String level;
	private Grid grid;
	private Stages stages;
	private Readys readys;

	private Logger logger;

	/**
	 * <pre>
	 * 기본 Jaxb 용
	 * </pre>
	 */
	public Round() {
		super();
		this.level = "normal";
		this.logger = LogUtil.getLogger(getClass());
	}

	/**
	 * <pre>
	 * 새로 추가 용
	 * </pre>
	 * 
	 * @param id
	 * @param level
	 * @param grid
	 * @param stages
	 * @param readys
	 */
	public Round(String id, String level, Grid grid, Stages stages, Readys readys) {
		this.id = id;
		this.level = (level != null) ? level : "normal";
		this.grid = grid;
		this.stages = stages;
		this.readys = readys;
	}

	public boolean validate() {
		if (this.grid != null && this.stages != null && this.readys != null) {
			List<String> gridcols = this.grid.getColors();
			List<String> stagescols = this.stages.getCols();
			List<String> readyscols = this.readys.getCols();
			if ((gridcols.size() * 2) == (stagescols.size() + readyscols.size())) {
				Map<String, Integer> gridMap = new HashMap<String, Integer>();
				for (String c : gridcols) {
					Coll.countUpInMap(gridMap, c);
				}
				Map<String, Integer> readyMap = new HashMap<String, Integer>();
				for (String c : stagescols) {
					Coll.countUpInMap(readyMap, c);
				}
				for (String c : readyscols) {
					Coll.countUpInMap(readyMap, c);
				}
				boolean bool = true;
				for (Iterator<String> it = gridMap.keySet().iterator(); it.hasNext();) {
					String color = it.next();
					Integer gcnt = gridMap.get(color);
					Integer rcnt = readyMap.get(color);
					if (gcnt == null) {
						gcnt = 0;
					}
					if (rcnt == null) {
						rcnt = 0;
					}
					int cnt = (gcnt * 2) - rcnt;
					if (0 < cnt) {
						if ("?".equals(color)) {
							continue;
						}
						this.logger.debug(Str.format("color[{}] ready need({})", color, cnt));
						bool = false;
					}
				}
				if (bool == false) {
					for (Iterator<String> it = readyMap.keySet().iterator(); it.hasNext();) {
						String color = it.next();
						Integer gcnt = gridMap.get(color);
						Integer rcnt = readyMap.get(color);
						if (gcnt == null) {
							gcnt = 0;
						}
						if (rcnt == null) {
							rcnt = 0;
						}
						int cnt = rcnt - (gcnt * 2);
						if (0 < cnt) {
							if ("?".equals(color)) {
								continue;
							}
							this.logger.debug(Str.format("color[{}] grid need({})", color, cnt / 2));
						}
					}
				}
				return bool;
			} else {
				this.logger.debug(Str.format("grid.size() = {}, stage.size() = {}, ready.size() = {}", gridcols.size(),
						stagescols.size(), readyscols.size()));
			}
		} else {
			this.logger.debug("데이터가 부족합니다.");
		}
		return false;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name = "level", required = false)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
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
