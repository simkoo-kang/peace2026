/**
 * RoundVo.java
 */
package kr.pe.game.shooter.vos;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 15.
 */
public class RoundVo implements Comparable<RoundVo> {

	private String id;
	private GridVo gridVo;
	private StageVo stageVo;
	private ReadyVo readyVo;

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 */
	public RoundVo(String id) {
		super();
		this.id = id;
		this.gridVo = null;
		this.stageVo = null;
		this.readyVo = null;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @param gridVo
	 * @param stageVo
	 * @param readyVo
	 */
	public RoundVo(String id, GridVo gridVo, StageVo stageVo, ReadyVo readyVo) {
		super();
		this.id = id;
		this.gridVo = gridVo;
		this.stageVo = stageVo;
		this.readyVo = readyVo;
	}

	/**
	 * <pre></pre>
	 * 
	 * @return
	 */
	public int getRound() {
		return Integer.valueOf(this.id);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the gridVo
	 */
	public GridVo getGridVo() {
		return this.gridVo;
	}

	/**
	 * @param gridVo the gridVo to set
	 */
	public void setGridVo(GridVo gridVo) {
		this.gridVo = gridVo;
	}

	/**
	 * @return the stageVo
	 */
	public StageVo getStageVo() {
		return this.stageVo;
	}

	/**
	 * @param stageVo the stageVo to set
	 */
	public void setStageVo(StageVo stageVo) {
		this.stageVo = stageVo;
	}

	/**
	 * @return the readyVo
	 */
	public ReadyVo getReadyVo() {
		return this.readyVo;
	}

	/**
	 * @param readyVo the readyVo to set
	 */
	public void setReadyVo(ReadyVo readyVo) {
		this.readyVo = readyVo;
	}

	@Override
	public int compareTo(RoundVo o) {
		int tr = this.getRound();
		int or = o.getRound();
		return (tr < or) ? -1 : (tr > or) ? 1 : 0;
	}
}
