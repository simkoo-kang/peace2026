/**
 * ShooterMain.java
 */
package kr.pe.game.shooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.game.shooter.base.Games;
import kr.pe.game.shooter.base.Grid;
import kr.pe.game.shooter.base.Readys;
import kr.pe.game.shooter.base.Round;
import kr.pe.game.shooter.base.Stages;
import kr.pe.util.Coll;
import kr.pe.util.Str;
import kr.pe.util.xml.Jaxb;

/**
 * <pre>
 * 
 * PC  2026. 10. 8. 오전 10:41:11
 * </pre>
 */
public class ShooterMain3 {

	private static int[] DX = new int[] { 0, 0, -1, 1 };
	private static int[] DY = new int[] { -1, 1, 0, 0 };

	private static Logger logger = LoggerFactory.getLogger(ShooterMain3.class);

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
	public ShooterMain3(String[][] matrix, String[] stages, String[] readys, int firingLinesCount) {
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
				if (cells[r][c].isAble() == false) {
					continue;
				}
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

	private boolean isValid = true;

	/**
	 * <pre>
	 * 1. 외부에서 최초 호출하는 진입점 메서드
	 * </pre>
	 * 
	 * @return
	 */
	public List<String> solve(Round round) {
		this.isValid = round.validate();
		logger.debug("validate={}", this.isValid);

		List<Shooter> shooterList = new ArrayList<Shooter>();

		// 최초 상태 생성
		SCell[][] initialCells = copyCells(this.cells);
		List<String> initialLoop = new ArrayList<>(this.loopStages);
		Queue<String> initialReady = new LinkedList<>(this.readyQueue);
		List<String> initialPaths = new ArrayList<>();

		// 재귀 호출 시작
		return dfs(initialCells, initialLoop, initialReady, shooterList, initialPaths);
	}

	private int callCount = 0;

	/**
	 * <pre>
	 * 게임을 완료했는지 검사
	 * </pre>
	 * 
	 * @param cels
	 * @param loopSts
	 * @param readyQue
	 * @param shts
	 * @return
	 */
	private boolean isCompleted(SCell[][] cels, List<String> loopSts, Queue<String> readyQue, List<Shooter> shts) {
		if (loopSts.size() < 1 && readyQue.size() < 1 && shts.size() < 1) {
			int cellCount = count(cels, this.row_nums, this.col_nums);
			if (cellCount == 0) {
				logger.debug("✨ [성공] 모든 격자 클리어! 정답을 반환합니다.[Up]");
				return true; // 최하단에서 성공한 경로를 그대로 반환
			}
			logger.debug("data error![Up]");
		}
		if (this.isValid == false) {
			List<SCell> ables = ableCells(cels, this.row_nums, this.col_nums);
			if (readyQue.size() < 1 || ables.size() < 1) {
				// 게임 데이터가 완전하지 않고, 더 이상 보충할 color 가 없으면
				return true;
			}
		}
		return false; // 격자가 남았다면 실패 경로이므로 null 반환
	}

	/**
	 * <pre>
	 * copy shooters
	 * </pre>
	 * 
	 * @param list
	 * @return
	 */
	private List<Shooter> copyShooters(List<Shooter> list) {
		List<Shooter> newlist = new ArrayList<Shooter>();
		for (Shooter s : list) {
			newlist.add(new Shooter(s.getColor(), s.getBullets())); // 깊은 복사
		}
		return newlist;
	}

	/**
	 * <pre>
	 * 2. 실제 재귀적으로 작동하는 탐색 메서드
	 * </pre>
	 * 
	 * @param cels
	 * @param loopSts
	 * @param readyQue
	 * @param shts
	 * @param paths
	 * @return
	 */
	private List<String> dfs(SCell[][] cels, List<String> loopSts, Queue<String> readyQue, List<Shooter> shts,
			List<String> paths) {
		logger.debug("start dfs [call count = {}]-------------------------------", this.callCount++);

		// [종료 조건 확인 1] 스테이지와 슈터가 모두 비었을 때
		if (isCompleted(cels, loopSts, readyQue, shts)) {
			return paths;
		}
//		if (loopSts.size() < 1 && readyQue.size() < 1 && shts.size() < 1) {
//			int cellCount = count(cels, this.row_nums, this.col_nums);
//			if (cellCount == 0) {
//				logger.debug("✨ [성공] 모든 격자 클리어! 정답을 반환합니다.[Up]");
//				return paths; // 최하단에서 성공한 경로를 그대로 반환
//			}
//			logger.debug("data error![Up]");
//			return null; // 격자가 남았다면 실패 경로이므로 null 반환
//		}

		// 1. 슈터가 있으면 발사 및 처리 (원본을 해치지 않게 로컬 스코프에서 복사 후 진행)
		List<String> nextLoopSts = new ArrayList<>(loopSts);
		Queue<String> nextReadyQue = new LinkedList<>(readyQue);
		List<Shooter> nextShts = copyShooters(shts);

		if (0 < nextShts.size()) {
			List<Shooter> removeSter = new ArrayList<Shooter>();
			boolean fired = false;

			for (Shooter shooter : nextShts) {
				if (shooter.hasPower() == false) {
					removeSter.add(shooter);
					continue;
				}
				String scolor = shooter.getColor();
				while (nextLoopSts.contains(scolor)) {
					if (shooter.killing()) {
						nextLoopSts.remove(scolor);
						fired = true;
						if (shooter.hasPower() == false) {
							removeSter.add(shooter);
							break;
						}
					}
				}
			}

			if (fired) {
				while (nextLoopSts.size() < this.loopStagesCount && nextReadyQue.isEmpty() == false) {
					nextLoopSts.add(nextReadyQue.poll());
				}
				while (0 < removeSter.size() && 0 < nextShts.size()) {
					Shooter shooter = removeSter.removeFirst();
					nextShts.remove(shooter);
					logger.debug(shooter.toString());
				}
			}
		}

		// [종료 조건 확인 2] 발사 연산 이후 다시 한번 체크
		if (isCompleted(cels, nextLoopSts, nextReadyQue, nextShts)) {
			return paths;
		}
//		if (nextLoopSts.size() < 1 && nextReadyQue.size() < 1 && nextShts.size() < 1) {
//			int cellCount = count(cels, this.row_nums, this.col_nums);
//			if (cellCount == 0) {
//				logger.debug("✨ [성공] 발사 후 클리어! 정답을 반환합니다.[Down]");
//				return paths;
//			}
//			logger.debug("data error![Down]");
//			return null;
//		}

		// 2. 대포 장전 로직 (의도대로 가장 점수 높은 '딱 하나'만 골라 장전 후 다음 단계 재귀 호출)
		if (nextShts.size() < this.firingLinesCount) {
			Map<String, Integer> loopColorMap = toMap(nextLoopSts);
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
			} else {
				// [종료 조건 확인 2] 발사 연산 이후 다시 한번 체크
				if (isCompleted(cels, nextLoopSts, nextReadyQue, nextShts)) {
					return paths;
				}
				logger.debug("더 이상 장전할 수 있는 블록이 없습니다. (막힌 길)");
				return null;
			}

			// 정렬 후 최고의 블록 '하나'만 선택
			ableMap = Coll.sortMapByValueReverseOrder(ableMap);
//			Map.Entry<SCell, Integer> entry = ableMap.entrySet().iterator().next();
//			SCell colorCell = entry.getKey();
			for (Iterator<SCell> it = ableMap.keySet().iterator(); it.hasNext();) {
				List<Shooter> currShooters = copyShooters(nextShts);
				Queue<String> currReadyQue = new LinkedList<String>(nextReadyQue);
				List<String> currLoop = new ArrayList<String>(nextLoopSts);

				SCell colorCell = it.next();
				// 데이터 상태 변화 적용
				currShooters.add(new Shooter(colorCell.getColor()));
				SCell[][] currCels = copyCells(cels);
				currCels[colorCell.getR()][colorCell.getC()].setColor(".");

				String path = Str.format("📥 하단 격자 [{}행, {}열] 사각형 [{}] 블록 터치 ➔ 대포 장전", colorCell.getR() + 1,
						colorCell.getC() + 1, colorCell.getColor());
				logger.debug(path);

				List<String> currPaths = new ArrayList<>(paths);
				currPaths.add(path);

				// 🔄 [핵심 변경] 다음 상태를 가지고 자기 자신을 호출 (재귀 탐색)
				List<String> result = dfs(currCels, currLoop, currReadyQue, currShooters, currPaths);
				if (result != null) {
					return result;
				}
			}
		} else {
			logger.debug("대포 슬롯이 꽉 찼습니다. (진행 불가)");
		}
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
//
//		String[][] matrix = new String[][] { { "Y", "R", "G", "B" } };
//		int firingLinesCount = 4;
//		String[] stages = new String[] { "R", "R", "Y", "Y", "G", "G", "B", "B" };
//		String[] readys = new String[] {};
//
//		// 1. 전체 게임 데이터 관리 객체 생성
//		Games games = new Games();

		// ----------------------------------------------------
		// [라운드 1 추가] 생성자와 헬퍼 메서드로 가독성 있게 조립
		// ----------------------------------------------------
//		Grid grid1 = new Grid(1, 4);
//		grid1.addRow(new Row("Y", "R", "G", "B")); // row 추가
//
//		Stages stages1 = new Stages(8, "R", "R", "Y", "Y", "G", "G", "B", "B");
//		Readys readys1 = new Readys(0); // 빈 데이터
//
//		games.addRound(new Round("1", grid1, stages1, readys1));

		// ----------------------------------------------------
		// [라운드 2 추가] 행이 여러 개인 격자(Grid) 구조 데이터 조립
		// ----------------------------------------------------
//		Grid grid2 = new Grid(3, 2);
//		grid2.addRow(new Row("G", "B"));
//		grid2.addRow(new Row("#", "R"));
//		grid2.addRow(new Row("#", "B"));
//
//		Stages stages2 = new Stages(8, "B", "R", "B", "G", "B", "R", "B", "G");
//		Readys readys2 = new Readys(0);
//
//		games.addRound(new Round("2", grid2, stages2, readys2));

		// ----------------------------------------------------
		// [라운드 3 추가] 개발자가 앞으로 계속 아래에 추가해 나가는 영역
		// ----------------------------------------------------
//		Grid grid3 = new Grid(3, 3);
//		grid3.addRow(new Row("Y", "V", "B"));
//		grid3.addRow(new Row("#", "R", "#"));
//		grid3.addRow(new Row("#", "Y", "#"));
//
//		Stages stages3 = new Stages(7, "Y", "R", "Y", "B", "V", "Y", "V");
//		Readys readys3 = new Readys(3, "B", "Y", "R");
//
//		games.addRound(new Round("3", grid3, stages3, readys3));

		// ----------------------------------------------------
		// 최종 확인: 조립된 자바 객체를 단 한 줄로 XML String 변환
		// ----------------------------------------------------
//		String finalXml = Jaxb.convertToXml(games);
//		System.out.println(finalXml);
//
		String filePath = "./conf/games/shooter/games_data.xml";

//		// ====================================================
//		// [1단계] 최초 1회: 자바 코드로 초기 데이터를 만들어 파일로 저장하기
//		// ====================================================
//		Games initialGames = new Games();
//
//		appendRound(initialGames, matrix, stages, readys);
//
//		Jaxb.convertToFile(initialGames, filePath);

		// ====================================================
		// [2단계] 일상 운영: 저장된 XML 파일을 읽어와서 프로그램에 공급하기
		// ====================================================
		System.out.println("\n🔄 외부 XML 파일로부터 데이터를 불러옵니다...");
		Games games = Jaxb.convertFromFile(filePath, Games.class);

		// 불러온 데이터 확인 테스트
		System.out.println("총 로드된 라운드 수: " + games.getRounds().size() + "개");
//		for (Round r : games.getRounds()) {
//			System.out.println(" - 라운드 [" + r.getId() + "] 불러오기 성공");
//		}

		String id = "24";
		Round round = games.getRound(id);
//		if (round.validate() == false) {
//			logger.debug("table[{}] is not valid!", id);
//			return;
//		}
		Stages stage = round.getStages();
		Readys ready = round.getReadys();
		Grid grid = round.getGrid();

		String[] stages = stage.getColsArray();
		String[] readys = ready.getColsArray();
		String[][] matrix = grid.getGrid();

		int firingLinesCount = 4;
		logger.debug("round [{}] 시작합니다.------", id);
		ShooterMain3 solver = new ShooterMain3(matrix, stages, readys, firingLinesCount);
		List<String> paths = solver.solve(round);
		if (paths == null || paths.size() == 0) {
			logger.debug("path not found!");
		} else {
			for (String path : paths) {
				logger.debug(path);
			}
		}
	}
}
