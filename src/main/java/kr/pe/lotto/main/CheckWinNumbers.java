/**
 * CheckWinNumbers.java
 */
package kr.pe.lotto.main;

import java.io.File;
import java.io.IOException;
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
	private List<NumberVO> loadFromFile(String filename, int round) {
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

	/**
	 * <pre></pre>
	 * 
	 * @param filename
	 * @return
	 */
	public List<String> checkMake(String filename) {
		NumberInfoVO lastVo = this.getLastVo();
		int round = lastVo.getRound();
		this.logger.debug(lastVo.toStringSum());
		this.logger.debug("" + round);

		List<String> res = new ArrayList<String>();

		filename = filename.replace("{round}", "" + round);
		List<NumberVO> numList = this.loadFromFile(filename, round);
		if (Str.isEmpty(numList)) {
			this.logger.debug("There's nothing to do.!");
			return res;
		}

		int[] selected = getNumber(numList);
		this.logger.debug("select: {}, matched: {}", selected.length, Arr.contains(selected, lastVo.getNums6()));
		res.add(Arr.toStringIntArray(selected, ","));
		res.add(Arr.toStringIntArray(Arr.arrAnd(selected, lastVo.getNums6()), ","));
		res.add(Str.format("select: {}, matched: {}", selected.length, Arr.contains(selected, lastVo.getNums6())));
		res.add("make size = " + numList.size());
		res.add("");

		int[] wins = new int[] { 0, 0, 0, 0, 0 };
		Map<Integer, List<CountMoneyVO>> map = new HashMap<>();
		int[] winNums = lastVo.getNumberVO().getNums6();
		int bonusNumber = lastVo.getBonus();
		CheckWin check = new CheckWin(winNums, bonusNumber);
		for (NumberVO vo : numList) {
			int rank = check.check(vo.getNums());
			if (0 < rank) {
				wins[rank - 1]++;
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

		boolean iswin = false;
		for (int i = 0; i < wins.length; i++) {
			if (0 < wins[i]) {
				res.add((i + 1) + "등 " + Str.numberFormat(wins[i]));
				iswin = true;
			}
		}
		if (!iswin) {
			res.add("당첨 내역이 없습니다.");
		} else {
			res.add("");
		}
		return res;
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

		List<String> res = new ArrayList<String>();
		NumberInfoVO ivo = check.getLastVo();
		res.add("round: " + (ivo.getRound()));
		res.add("당첨 번호: " + ivo.toStringSum(true));
		res.add("");

		String filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}.txt";
		List<String> res1 = check.checkMake(filename);
		res.add("select ---------------------");
		res.addAll(res1);
		res.add("");

		filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_all.txt";
		List<String> res2 = check.checkMake(filename);
		res.add("all ---------------------");
		res.addAll(res2);

		filename = "D:\\Workspace\\docs\\lotto\\result.txt";
		try {
			FileUtil.writeln(filename, Str.join(res, Str.CRLF));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_filtered.txt";
//		List<String> res3 = check.checkMake(filename);

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
