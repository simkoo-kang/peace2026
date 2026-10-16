/**
 * MakeFromPythonAll.java
 */
package kr.pe.lotto.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import kr.pe.lotto.LottoCoreFunctions;
import kr.pe.lotto.LottoMain;
import kr.pe.lotto.filter.LottoFilter;
import kr.pe.lotto.filter.range.SumRangeFilter;
import kr.pe.lotto.util.LottoUtil;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;
import kr.pe.util.Arr;
import kr.pe.util.Str;
import kr.pe.util.rnd.Rnd;

/**
 * <pre>
 * 
 * PC  2026. 10. 8. 오후 7:20:03
 * </pre>
 */
public class MakeFromPythonAll extends LottoMain {

	private LottoCoreFunctions core = null;

	/**
	 * <pre></pre>
	 * 
	 * @param highSum
	 * @param lowSum
	 */
	public MakeFromPythonAll(int lowSum, int highSum) {
		super();

		this.core = LottoCoreFunctions.getInstance(this.lottoList);
		this.core.addFilter(8, new SumRangeFilter("Sum_00", lowSum, highSum)); // 94%
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 10. 8. 오후 7:26:51
	 * </pre>
	 *
	 * @param allname
	 * @param filteredname
	 * @param filename
	 * @param games
	 */
	private void execute(String allname, String filteredname, String filename, int games) {
		NumberInfoVO lastVo = this.getLastVo();
		int round = lastVo.getRound() + 1;
		String roundStr = String.valueOf(round);
		String allFilename = allname.replace("{round}", roundStr);
		String filteredFilename = filteredname.replace("{round}", roundStr);
		String currentFilename = filename.replace("{round}", roundStr);

//		filename = filename.replace("{round}", "" + round);
		List<NumberVO> numList = LottoUtil.loadNumbers(allFilename);
		if (Str.isEmpty(numList)) {
			this.logger.debug("There's nothing in file[{}].!", allFilename);
			return;
		}

		List<LottoFilter> filters = this.core.getFilters();
		List<NumberVO> filtered_vos = new ArrayList<>();
		for (NumberVO vo : numList) {
			if (LottoUtil.isFilter(filters, vo)) {
				filtered_vos.add(vo);
			}
		}

		this.toSave(filteredFilename, filtered_vos, false);

		int size = filtered_vos.size();

		List<NumberVO> select_vos = new ArrayList<>();
		Set<String> sets = new HashSet<>();
		Random rand = Rnd.getRandom();
		int counts = 0;
		while (counts < games) {
			NumberVO vo = filtered_vos.get(rand.nextInt(size));
			if (!sets.contains(vo.toString())) {
				select_vos.add(vo);
				sets.add(vo.toString());
				counts++;
			}
		}

		Collections.sort(select_vos);

		StringBuilder sb = new StringBuilder();
		Map<Integer, Integer> map = new HashMap<>();
		String spc = "";
		for (int i = 0; i < games; i++) {
			NumberVO vo = select_vos.get(i);
			for (int n : vo.getNums6()) {
				Integer count = map.get(n);
				if (count == null) {
					count = 0;
				}
				count++;
				map.put(n, count);
			}
			sb.append(spc).append(vo.toStringSum()).append("\t1");
			spc = "\n";
			if (i == 2 || i == 5 || i == 8) {
				sb.append(spc).append("");
			}
		}

		this.logger.debug(sb.toString());

		sb.append(spc).append("03-04-17-24-26-41	115	1");
		sb.append(spc).append("06-10-13-32-38-45");
		sb.append(spc).append("09-12-25-27-30-32");
		sb.append(spc).append("");
		sb.append(spc).append("04-08-13-22-31-44	122	1");
		sb.append(spc).append("09-15-19-23-33-42");
		sb.append(spc).append("09-15-19-23-33-42");
		sb.append(spc).append("");

		sb.append(spc).append(games + 6).append(" 게임");

		sb.append(spc).append("");
		sb.append(spc).append("총 ").append(map.size()).append("개 번호가 선택되었습니다.");
		int[] arr = Arr.toIntArray(map.keySet());
		Arrays.sort(arr);
		sb.append(spc).append(Str.join(arr, ", "));

		sb.append(spc).append("");
		for (int n : arr) {
			sb.append(spc).append(n).append(" = ").append(map.get(n));
		}

		this.toSave(currentFilename, sb.toString(), false);

		this.logger.debug(allFilename);
		this.logger.debug(filteredFilename);
		this.logger.debug(currentFilename);
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 10. 8. 오후 7:20:03
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}.txt";
		String allname = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_all.txt";
		String filteredname = "D:\\Workspace\\vscode\\lotto\\conf\\make\\{round}_filtered.txt";

		int lowSum = 90, highSum = 179;
		int games = 9;
		MakeFromPythonAll make = new MakeFromPythonAll(lowSum, highSum);
		make.execute(allname, filteredname, filename, games);
	}
}
