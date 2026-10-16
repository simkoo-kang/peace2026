/**
 * Games.java
 */
package kr.pe.game.shooter.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 14.
 */
@XmlRootElement(name = "games")
public class Games {
	private List<Round> rounds;

	public Games() {
		super();
		this.rounds = new ArrayList<Round>();
	}

	@XmlElement(name = "round")
	public List<Round> getRounds() {
		return this.rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param id
	 * @return
	 */
	public Round getRound(String id) {
		for (Round round : this.rounds) {
			if (round.getId().equals(id)) {
				return round;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * 편의 메서드: 새로운 라운드를 리스트에 바로 추가
	 * </pre>
	 * 
	 * @param round
	 */
	public void addRound(Round round) {
		this.rounds.add(round);
	}
}
