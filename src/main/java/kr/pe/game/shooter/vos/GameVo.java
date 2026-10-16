/**
 * GameVo.java
 */
package kr.pe.game.shooter.vos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public class GameVo {

	private Set<String> sets;
	private Map<String, RoundVo> roundMap;

	/**
	 * <pre></pre>
	 */
	public GameVo() {
		this.sets = new HashSet<String>();
		this.roundMap = new HashMap<String, RoundVo>();
	}

	/**
	 * <pre></pre>
	 * 
	 * @param vo
	 * @return
	 */
	public boolean addRound(RoundVo vo) {
		if (this.sets.add(vo.getId())) {
			this.roundMap.put(vo.getId(), vo);
			return true;
		}
		return false;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @return
	 */
	public RoundVo removeRound(String id) {
		if (this.sets.contains(id)) {
			this.sets.remove(id);
			return this.roundMap.remove(id);
		}
		return null;
	}

	/**
	 * @return the sets
	 */
	public Set<String> getSets() {
		return this.sets;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @return
	 */
	public RoundVo getRound(String id) {
		if (this.sets.contains(id)) {
			return this.roundMap.get(id);
		}
		return null;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param round
	 * @return
	 */
	public RoundVo getRound(int round) {
		return getRound(String.valueOf(round));
	}

	/**
	 * @return the roundList
	 */
	public List<RoundVo> getRoundList() {
		return new ArrayList<RoundVo>(this.roundMap.values());
	}

	/**
	 * @param roundList the roundList to set
	 */
	public void setRoundList(List<RoundVo> roundList) {
		if (0 < this.sets.size()) {
			this.sets.clear();
			this.roundMap.clear();
		}
		for (RoundVo vo : roundList) {
			addRound(vo);
		}
	}
}
