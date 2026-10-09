/**
 * ShooterMain.java
 */
package kr.pe.game.shooter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.Str;

/**
 * <pre>
 * 
 * PC  2026. 10. 8. 오전 10:41:11
 * </pre>
 */
public class ShooterMain {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String[][] matrix;
	private int firingLinesCount;

	private List<String> loopStages;
	private Queue<String> readyQueue;

	private int row_nums, col_nums;

	/**
	 * <pre></pre>
	 * 
	 * @param matrix
	 * @param stages
	 * @param readys
	 * @param firingLinesCount
	 */
	public ShooterMain(String[][] matrix, String[] stages, String[] readys, int firingLinesCount) {
		this.matrix = matrix;
		this.firingLinesCount = firingLinesCount;

		if (matrix != null) {
			this.row_nums = matrix.length;
			this.col_nums = (0 < matrix.length) ? matrix[0].length : 0;
		} else {
			this.row_nums = this.col_nums = 0;
		}

		this.loopStages = new ArrayList<>();
		if (!Str.isEmpty(stages)) {
			for (String stage : stages) {
				this.loopStages.add(stage);
			}
		}
		this.readyQueue = new LinkedList<>();
		if (!Str.isEmpty(readys)) {
			for (String ready : readys) {
				this.readyQueue.offer(ready);
			}
		}

		this.solve();
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 10. 8. 오후 3:00:18
	 * </pre>
	 *
	 */
	private void solve() {

	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 10. 8. 오전 10:41:11
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String[][] matrix = new String[][] {
				{ "O", "B", "P", "R" },
				{ "Y", "V", "R", "C" },
				{ "#", "Y", "V", "BR" },
				{ "#", "#", "B", "P" },
				{ "#", "W", "B", "G" }
		};
		int firingLinesCount = 4;
		String[] stages = new String[] { "R", "Y", "B", "O", "G", "C", "W" };
		String[] readys = new String[] {
				"G", "P", "BR", "Y", "G", "BR", "B", "V", "P", "W", "B", "Y", "R", "p", "P", "Y", "R", "V", "C", "G",
				"O", "V", "R", "B", "V"
		};

		new ShooterMain(matrix, stages, readys, firingLinesCount);
	}
}
