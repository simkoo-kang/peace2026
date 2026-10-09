/**
 * CheckWinNumbers.java
 */
package kr.pe.lotto.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.pe.lotto.LottoMain;
import kr.pe.lotto.LottoNumberException;
import kr.pe.lotto.util.CheckWin;
import kr.pe.lotto.vo.CountMoneyVO;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;
import kr.pe.util.Arr;
import kr.pe.util.Str;
import kr.pe.util.io.FileUtil;

/**
 * PC 2026. 8. 10. 오후 4:19:19
 */
public class CheckWinNumbers extends LottoMain {

	/**
	 * 
	 */
	public CheckWinNumbers() {
		super();
	}

	/**
	 * @param filename
	 * @param round
	 * @return
	 */
	private List<NumberVO> getNumbersForMake(String filename, int round) {
		File file = new File(filename);
		if (!file.exists()) {
			this.logger.debug("{} not found", filename);
			return null;
		}
		this.logger.debug(filename);

		List<NumberVO> list = new ArrayList<>();
		List<String> linelist = FileUtil.readAll(filename);
		this.logger.debug("{}", linelist.size());

		for (String line : linelist) {
			if (Str.isEmpty(line)) {
				continue;
			}
			try {
				NumberVO vo = new NumberVO(line);
				list.add(vo);
			} catch (LottoNumberException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param map
	 */
	private void loggerRank(Map<Integer, List<CountMoneyVO>> map) {
		if (map.size() == 0) {
			this.logger.debug("There are no winning numbers..!");
			return;
		}
		for (int rank = 1; rank < 6; rank++) {
			List<CountMoneyVO> mvoList = map.get(rank);
			if (mvoList != null) {
				int size = mvoList.size();
				long money = 0;
				if (size == 1) {
					money = mvoList.get(0).getMoney();
				} else {
					switch (rank) {
					case 1:
					case 2:
					case 3:
						money = sumMoney(mvoList);
						break;
					case 4:
						money = size * 50000;
						break;
					case 5:
						money = size * 5000;
						break;
					}
				}
				this.logger.debug("rank = {}, count = {}, money = {}", rank, size, Str.numberFormat(money));
			}
		}
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @param mvoList
	 * @return
	 */
	private long sumMoney(List<CountMoneyVO> mvoList) {
		long money = 0;
		for (CountMoneyVO cmvo : mvoList) {
			money += cmvo.getMoney();
		}
		return money;
	}

	public void checkMake(String filename) {
		NumberInfoVO lastVo = this.getLastVo();
		int round = lastVo.getRound();
		this.logger.debug(lastVo.toStringSum());
		this.logger.debug("" + round);

		filename = filename.replace("{round}", "" + round);
		List<NumberVO> numList = this.getNumbersForMake(filename, round);
		if (Str.isEmpty(numList)) {
			this.logger.debug("There's nothing to do.!");
			return;
		}

		int[] selected = getNumber(numList);
		this.logger.debug("select: {}, matched: {}", selected.length, Arr.contains(selected, lastVo.getNums6()));

		Map<Integer, List<CountMoneyVO>> map = new HashMap<>();
		int[] winNums = lastVo.getNumberVO().getNums6();
		int bonusNumber = lastVo.getBonus();
		CheckWin check = new CheckWin(winNums, bonusNumber);
		for (NumberVO vo : numList) {
			int rank = check.check(vo.getNums());
			if (0 < rank) {
				List<CountMoneyVO> list = map.get(rank);
				if (list == null) {
					list = new ArrayList<>();
					map.put(rank, list);
				}
				list.add(lastVo.getCountMoneyList().get(rank - 1));
			}
		}
		loggerRank(map);
		this.logger.debug("make size = {}", numList.size());
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 14. 오후 4:39:02
	 * </pre>
	 *
	 * @param numList
	 * @return
	 */
	private int[] getNumber(List<NumberVO> numList) {
		Set<Integer> set = new HashSet<>();
		for (NumberVO vo : numList) {
			int[] nums = vo.getNums6();
			for (int i = 0; i < 6; i++) {
				set.add(nums[i]);
			}
			if (set.size() == 45) {
				break;
			}
		}
		int[] nums = Arr.toIntArray(set);
		Arrays.sort(nums);
		return nums;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CheckWinNumbers check = new CheckWinNumbers();

		String filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}.txt";
		check.checkMake(filename);

		filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_all.txt";
		check.checkMake(filename);

		filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_filtered.txt";
		check.checkMake(filename);

//		String text = "올해 2026년 내 나이는 30세입니다.";
//
//		// 연속된 숫자(\\d+)를 찾는 패턴 생성
//		Pattern pattern = Pattern.compile("\\d+");
//		Matcher matcher = pattern.matcher(text);
//
//		// 매칭되는 숫자를 순서대로 출력
//		while (matcher.find()) {
//			System.out.println("찾은 숫자: " + matcher.group());
//		}
	}
}
