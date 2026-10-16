/**
 * ShooterMain.java
 */
package kr.pe.game.shooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.util.Coll;
import kr.pe.util.Str;

/**
 * <pre>
 * 
 * PC  2026. 10. 8. 오전 10:41:11
 * </pre>
 */
public class ShooterMain2 {

	private static int[] DX = new int[] { 0, 0, -1, 1 };
	private static int[] DY = new int[] { -1, 1, 0, 0 };

	private static Logger logger = LoggerFactory.getLogger(ShooterMain2.class);

	private SCell[][] cells;
	private int firingLinesCount;

	private List<String> loopStages;
	private int loopStagesCount;
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
	public ShooterMain2(String[][] matrix, String[] stages, String[] readys, int firingLinesCount) {
		this.cells = convert(matrix);
		this.firingLinesCount = firingLinesCount;
		this.loopStagesCount = stages.length;

		if (matrix != null) {
			this.row_nums = matrix.length;
			this.col_nums = (0 < this.row_nums) ? matrix[0].length : 0;
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
	 * <pre></pre>
	 * 
	 * @param matrix
	 * @return
	 */
	private SCell[][] convert(String[][] matrix) {
		int rows = matrix.length;
		if (rows < 1) {
			return new SCell[0][0];
		}
		int cols = matrix[0].length;
		if (cols < 1) {
			return new SCell[0][0];
		}
		SCell[][] cells = new SCell[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				cells[r][c] = new SCell(matrix[r][c], r, c);
			}
		}
		return cells;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param rows
	 * @param cols
	 * @return
	 */
	public List<SCell> ableCells(SCell[][] cells, int rows, int cols) {
		List<SCell> list = new ArrayList<SCell>();
		Set<String> sets = new HashSet<String>();
		int r = 0;
		int dots = 0;
		for (int c = 0; c < cols; c++) {
			SCell cell = cells[r][c];
			if (cell.isWall()) {
				continue;
			}
			if (cell.isEmpty()) {
				dots++;
				continue;
			}
			sets.add(r + "-" + c);
			list.add(cell);
		}
		// 처음에는 첫 줄에 빈 칸[.]이 없다. 따라서 다음 줄의 cell은 선택할 수 없다.
		if (dots == 0) {
			return list;
		}
		String key;
		for (r = 1; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				key = r + "-" + c;
				if (sets.contains(key)) {
					continue;
				}
				for (int i = 0; i < 4; i++) {
					int dr = r + DX[i];
					int dc = c + DY[i];
					if (dr < 0 || rows <= dr || dc < 0 || cols <= dc) {
						continue;
					}
					if (cells[dr][dc].isEmpty()) {
						list.add(cells[r][c]);
						break;
					}
				}
			}
		}
		return list;
	}

	private class Status {
		SCell[][] cells;
		List<String> loopStages;
		Queue<String> readyQueue;
		List<Shooter> shooterList;
		List<String> path;
		Set<String> sets;

		/**
		 * <pre></pre>
		 * 
		 * @param cells
		 * @param loopStages
		 * @param readyQueue
		 * @param sets
		 * @param shooterList
		 */
		public Status(SCell[][] cells, List<String> loopStages, Queue<String> readyQueue, Set<String> sets,
				List<Shooter> shooterList) {
			this(cells, loopStages, readyQueue, shooterList, sets, new ArrayList<String>());
		}

		/**
		 * <pre></pre>
		 * 
		 * @param cels
		 * @param loopSts
		 * @param readyQue
		 * @param shts
		 * @param sets
		 * @param paths
		 */
		public Status(SCell[][] cells, List<String> loopStages, Queue<String> readyQueue, List<Shooter> shooterList,
				Set<String> sets, List<String> paths) {
			super();
			this.cells = cells;
			this.loopStages = loopStages;
			this.readyQueue = readyQueue;
			this.shooterList = shooterList;
			this.sets = sets;
			this.path = paths;
		}

		void addPath(String path) {
			this.path.add(path);
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * @param cells
	 * @return
	 */
	private SCell[][] copyCells(SCell[][] cells) {
		// 원본 데이터가 담긴 cells를 스트림으로 변환합니다.
		return Arrays.stream(cells).map(row -> Arrays.stream(row).map(SCell::copyMe) // 1차원 배열(행) 안의 개별 SCell 객체 복사
				.toArray(SCell[]::new)).toArray(SCell[][]::new);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 10. 8. 오후 3:00:18
	 * </pre>
	 *
	 */
	private List<String> solve() {
		Set<String> sets = new HashSet<String>();
		List<Shooter> shooterList = new ArrayList<Shooter>();
		Status status = new Status(this.cells, this.loopStages, this.readyQueue, sets, shooterList);
		Queue<Status> que = new LinkedList<ShooterMain2.Status>();
		que.offer(status);

		while (que.isEmpty() == false) {
			status = que.poll();

			// 큐에서 꺼낸 직후 안전하게 복사하여 이 루프 안에서만 독립적으로 사용
			SCell[][] cels = copyCells(status.cells);
			List<String> loopSts = new ArrayList<String>(status.loopStages);
			Queue<String> readyQue = new LinkedList<String>(status.readyQueue);
			List<Shooter> shts = new ArrayList<Shooter>(status.shooterList);
			List<String> paths = new ArrayList<String>(status.path);
			Set<String> set = new HashSet<String>(status.sets);

			Map<String, Integer> loopColorMap = new HashMap<String, Integer>();

			// 첫 번째 종료 조건 확인
			if (loopSts.size() < 1) {
				if (readyQue.size() < 1) {
					if (shts.size() < 1) {
						int cellCount = count(cels, this.row_nums, this.col_nums);
						if (cellCount == 0) {
							que.clear(); // 정답을 찾았으므로 큐를 비우고 즉시 종료
							logger.debug("정답을 찾아 종료합니다.[상단]");
							return paths;
						}
					}
				}
				logger.debug("데이타 불일치 입니다.[상단]");
				// ❌ return null; -> ⭕ 정답이 아니므로 현재 갈래를 버리고 큐의 다음 상태로 넘어감
//				continue;
			}

			// shooter가 있으면 발사 및 처리
			if (0 < shts.size()) {
				List<Shooter> removeSter = new ArrayList<Shooter>();
				boolean fired = false;
				for (Shooter shooter : shts) {
					if (shooter.hasPower() == false) {
						removeSter.add(shooter);
						logger.debug("{} 에너지가 없어요.", shooter.toString());
						continue;
					}
					String scolor = shooter.getColor();
					while (loopSts.contains(scolor)) {
						if (shooter.killing()) {
							loopSts.remove(scolor);
							fired = true;
							if (shooter.hasPower() == false) {
								removeSter.add(shooter);
								logger.debug("{} 에너지를 모두 소비했어요.", shooter.toString());
								break;
							}
						}
					}
				}
				if (fired) {
					boolean bool = false;
					while (loopSts.size() < this.loopStagesCount && readyQue.isEmpty() == false) {
						loopSts.add(readyQue.poll());
						bool = true;
					}
					if (bool) {
						logger.debug("보충이 완료 되었습니다.");
						bool = false;
					}
					while (0 < removeSter.size() && 0 < shts.size()) {
						Shooter shooter = removeSter.removeFirst();
						shts.remove(shooter);
						bool = true;
					}
					if (bool) {
						logger.debug("소진된 슈터 제거 완료.");
					}
				}
			}

			// 두 번째 종료 조건 확인 (발사 후 상태 변화 체크)
			if (loopSts.size() < 1) {
				if (readyQue.size() < 1) {
					if (shts.size() < 1) {
						int cellCount = count(cels, this.row_nums, this.col_nums);
						if (cellCount == 0) {
							que.clear();
							logger.debug("정답을 찾아 종료합니다.[하단]");
							return paths;
						}
					}
				}
				logger.debug("데이타 불일치 입니다.[하단]");
				// ❌ return null; -> ⭕ 다음 갈래 탐색을 위해 스킵
				continue;
			}

			// 대포 장전 가능 여부 확인 및 큐 추가
			if (shts.size() < this.firingLinesCount) {
				loopColorMap = toMap(loopSts);
				Map<SCell, Integer> ableMap = new HashMap<SCell, Integer>();
				List<SCell> ables = ableCells(cels, this.row_nums, this.col_nums);

				if (0 < ables.size()) {
					for (int i = 0; i < ables.size(); i++) {
						SCell sc = ables.get(i);
						Integer count = loopColorMap.get(sc.getColor());
						if (count == null) {
							count = 0;
						}
						count++;
						ableMap.put(sc, count);
					}
				}

				if (ableMap.size() < 1) {
					logger.debug("no more!");
					// 탐색이 막힌 갈래이므로 큐를 비우지 않고 현재 갈래만 버립니다.
					continue;
				}

				ableMap = Coll.sortMapByValueReverseOrder(ableMap);
				int cnt = 0;
//				Map.Entry<SCell, Integer> entry = ableMap.entrySet().iterator().next();
				for (Map.Entry<SCell, Integer> entry : ableMap.entrySet()) {
					SCell colorCell = entry.getKey();
					String key = colorCell.getR() + "-" + colorCell.getC();
					if (!set.add(key)) {
						logger.debug("{} 중복되어 스킵합니다.", colorCell.toString());
						continue;
					}

//					shts.add(new Shooter(colorCell.getColor()));
					List<Shooter> nextShts = new ArrayList<Shooter>(shts);
					nextShts.add(new Shooter(colorCell.getColor())); // 복사본에 대포 추가

					String path = Str.format("📥 하단 격자 [{}행, {}열] 사각형 [{}] 블록 터치 ➔ 대포 장전", colorCell.getR() + 1,
							colorCell.getC() + 1, colorCell.getColor());
					logger.debug(path);
					// 경로(paths)도 주소 공유를 막기 위해 새 리스트로 복사하여 전달합니다.
					List<String> nextPaths = new ArrayList<String>(paths);
					nextPaths.add(path);

					SCell[][] nextCels = copyCells(cels);
//					cels[colorCell.getR()][colorCell.getC()].setColor(".");
					nextCels[colorCell.getR()][colorCell.getC()].setColor(".");

					// 현재까지의 상태와 새로운 경로(path)를 담아 다음 탐색 등록
					Status sts = new Status(cels, loopSts, readyQue, shts, set, paths);
					sts.addPath(path);
					que.offer(sts);
					cnt++;
				}
				logger.debug("{}회 큐에 추가 하였습니다.", cnt);
			} else {
				logger.debug("shooter is full!");
				// 대포가 꽉 차서 더 이상 진행할 수 없는 갈래이므로 스킵
				continue;
			}
		}
		logger.debug("최종 종료입니다.===============");
		// 큐를 모두 돌았음에도 정답을 하나도 찾지 못한 경우에만 최종 null 반환
		return null;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, Integer> toMap(Collection<String> list) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String color : list) {
			map = Coll.plus(map, color);
		}
		return map;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param cels
	 * @param rowNums
	 * @param colNums
	 * @return
	 */
	private int count(SCell[][] cels, int rowNums, int colNums) {
		int cnt = 0;
		for (int r = 0; r < rowNums; r++) {
			for (int c = 0; c < colNums; c++) {
				if (cels[r][c].isAble()) {
					cnt++;
				}
			}
		}
		return cnt;
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
//		String[][] matrix = new String[][] { { "O", "B", "P", "R" }, { "Y", "V", "R", "C" }, { "#", "Y", "V", "BR" },
//				{ "#", "#", "B", "P" }, { "#", "W", "B", "G" } };
//		int firingLinesCount = 4;
//		String[] stages = new String[] { "R", "Y", "B", "O", "G", "C", "W" };
//		String[] readys = new String[] { "G", "P", "BR", "Y", "G", "BR", "B", "V", "P", "W", "B", "Y", "R", "p", "P",
//				"Y", "R", "V", "C", "G", "O", "V", "R", "B", "V" };

		String[][] matrix = new String[][] { { "Y", "R", "G", "B" } };
		int firingLinesCount = 4;
		String[] stages = new String[] { "R", "R", "Y", "Y", "G", "G", "B", "B" };
		String[] readys = new String[] {};

		ShooterMain2 solver = new ShooterMain2(matrix, stages, readys, firingLinesCount);
		List<String> paths = solver.solve();
		if (paths == null || paths.size() == 0) {
			logger.debug("path not found!");
		} else {
			for (String path : paths) {
				logger.debug(path);
			}
		}
	}
}
