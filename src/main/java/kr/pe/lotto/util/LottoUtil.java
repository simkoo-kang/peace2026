/**
 * LottoUtil.java
 */
package kr.pe.lotto.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.lotto.LottoNumberException;
import kr.pe.lotto.filter.LottoFilter;
import kr.pe.lotto.vo.CountMoneyVO;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;
import kr.pe.util.Arr;
import kr.pe.util.Obj;
import kr.pe.util.Str;
import kr.pe.util.io.FileUtil;
import kr.pe.util.io.TextReader;

/**
 * <pre></pre>
 * gbrai  2026. 10. 9.
 */
public final class LottoUtil {

	public static Logger logger = LoggerFactory.getLogger(LottoUtil.class);

	public final static String FIRST_LOTTO_DATE = "2002-12-07";
//	public final static String LOTTO_NUMBER_FILE = "./conf/lotto/numbers.txt";
	public final static String PATH_LOTTO_NUMBER_CONF = "./conf/lotto/";

	public final static String PATH_LOTTO_NUMBER_DOCS = "D:/Workspace/docs/lotto/";
	public final static String FILE_LOTTO_NUMBER = PATH_LOTTO_NUMBER_DOCS + "numbers.txt";
	// "./conf/lotto/weekly_numbers.txt"

	public static String NUMBER_TITLE = "회자	추첨일	N1	N2	N3	N4	N5	N6	BN	SUM	AC	L05	L10	1등	당첨금액	2등	당첨금액	2등	당첨금액	4등	당첨금액	5등	당첨금액	자동	수동	반자동";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	/**
	 * <pre>
	 * 전체 번호의 빈도수
	 * 
	 * PC 2026. 9. 2. 오후 6:35:47
	 * </pre>
	 *
	 * @param list
	 * @return
	 */
	public static Map<Integer, Integer> getNumberCountMap(List<NumberInfoVO> list) {
		Map<Integer, Integer> map = new HashMap<>();
		for (NumberInfoVO ivo : list) {
			int[] nums = ivo.getNums6();
			for (int i = 0; i < 6; i++) {
				Integer count = map.get(nums[i]);
				if (count == null) {
					count = 0;
				}
				count++;
				map.put(nums[i], count);
			}
		}
		return map;
	}

	/**
	 * <pre>
	 * [기능 2] '1,2,4' 또는 '1,3,4' 처럼 3개 숫자가 징검다리 형태로 붙어있는지 검사합니다.
	 * (정렬했을 때 간격이 [1, 2]이거나 [2, 1]인 연속된 3개의 숫자 그룹이 있는지 확인)
	 * 
	 * PC 2026. 8. 30. 오후 2:20:47
	 * </pre>
	 *
	 * @param nums
	 * @return
	 */
	public static boolean hasSteppingStonePattern(int[] nums) {
		for (int i = 0; i < 4; i++) {
			// 6개 번호 중 연속된 3개 숫자의 조합을 순차적으로 검사 (총 4개 그룹 가능)
			int a = nums[i], b = nums[i + 1], c = nums[i + 2];
			int diff1 = b - a, diff2 = c - b;
			// [1, 2] 패턴 (예: 1, 2, 4) 또는 [2, 1] 패턴 (예: 1, 3, 4)
			if ((diff1 == 1 && diff2 == 2) || (diff1 == 2 && diff2 == 1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * 연속된 3개의 숫자 그룹(예: 1-3-5, 14-16-18, 21-25-29, ...)이 존재하는지 검사합니다.
	 * 대각선 [1-9-17, 6-12-18, ...]
	 * 
	 * interval = 0이면 모든 동일 간격
	 * 
	 * PC 2026. 8. 30. 오후 2:08:41
	 * </pre>
	 *
	 * @param nums
	 * @param interval
	 * @return
	 */
	public static boolean hasEqualIntervalPattern(int[] nums, int interval) {
		if (interval == 0) {
			for (int i = 0; i < 4; i++) {
				// 6개 번호 중 연속된 3개 숫자의 조합을 순차적으로 검사 (총 4개 그룹 가능)
				int a = nums[i], b = nums[i + 1], c = nums[i + 2];
				if (b - a == c - b) {
					return true;
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				// 6개 번호 중 연속된 3개 숫자의 조합을 순차적으로 검사 (총 4개 그룹 가능)
				int a = nums[i], b = nums[i + 1], c = nums[i + 2];
				if (b - a == interval && c - b == interval) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * make full numbers
	 * 사용자가 고른 번호들 내에서 필터 조건을 만족하는 조합만 생성하여 반환
	 * 속도가 빠르다.
	 * </pre>
	 * 
	 * @param list
	 * @param cache
	 * @param filters
	 * @return
	 */
	public static List<NumberVO> getFullNumbers(List<Integer> list, List<String> cache, List<LottoFilter> filters) {
		return getFullNumbers(list, cache, filters, false, 0);
	}

	/**
	 * <pre>
	 * make full numbers
	 * 사용자가 고른 번호들 내에서 필터 조건을 만족하는 조합만 생성하여 반환
	 * 속도가 빠르다.
	 * </pre>
	 * 
	 * @param list
	 * @param cache
	 * @param filters
	 * @param isDebug
	 * @param stopCount
	 * @return
	 */
	public static List<NumberVO> getFullNumbers(List<Integer> list, List<String> cache, List<LottoFilter> filters,
			boolean isDebug, int stopCount) {
		long start = System.currentTimeMillis(), first = start, end;

		List<NumberVO> nlist = new ArrayList<NumberVO>();
		List<Integer> pool = new ArrayList<>(list);
		Collections.sort(pool);

		int n = pool.size();
		logger.debug("n={} {}", n, Str.timeToString2(first));
		logger.debug(Arr.toStringIntegerList(list, ","));

		int r = 6;
		if (n < r) {
			return new ArrayList<>();
		}

		int[] pointers = new int[r];
		for (int i = 0; i < r; i++) {
			pointers[i] = i;
		}

		int count = 0;
		int total = 0;
		while (true) {
			total++;

			// 1. 임시 조합 배열 생성
			int[] current = new int[r];
			for (int i = 0; i < r; i++) {
				current[i] = pool.get(pointers[i]);
			}

			// 2. [필터링 통과 여부 검사] - 모든 조건을 만족할 때만 리스트에 추가
			if (isFilter(filters, current)) {
				NumberVO vo = new NumberVO(current);
				if (!cache.contains(vo.toString())) {
					if (0 < stopCount && stopCount < count) {
						logger.debug("true: {}, stopCount: {}", vo.toStringSum(), stopCount);
						break;
					}
					nlist.add(vo);
					count++;
					if (count % 10000 == 0) {
						end = System.currentTimeMillis();
						logger.debug("{} count={} {} {}", Arr.toStringIntArray1(current), Str.numberFormat(count),
								Str.numberFormat(nlist.size()), Str.timeToString(end - first));
					}
				}
			} else {
				if (isDebug && filterJustExecuted != null) {
					Integer cnt = failedFiltersMap.get(filterJustExecuted.getName());
					if (cnt == null) {
						cnt = 0;
					}
					cnt++;
					failedFiltersMap.put(filterJustExecuted.getName(), cnt);

					if (total > 1000000) {
						logger.debug("{} fail to {}", Arrays.toString(current), filterJustExecuted.getName());
						break;
					}
				}
			}

			// 포인트 이동 로직
			int targetIdx = r - 1;
			while (0 <= targetIdx && pointers[targetIdx] == n - r + targetIdx) {
				targetIdx--;
			}
			if (targetIdx < 0) {
				break;
			}

			pointers[targetIdx]++;
			for (int i = targetIdx + 1; i < r; i++) {
				pointers[i] = pointers[i - 1] + 1;
			}
		}
		end = System.currentTimeMillis();
		logger.debug("count={} {} {}", Str.numberFormat(count), Str.numberFormat(nlist.size()),
				Str.timeToString(end - first));
		return nlist;
	}

	// for debug
	private static Map<String, Integer> failedFiltersMap = new HashMap<>();
	private static LottoFilter filterJustExecuted = null;
	
	/**
	 * <pre>
	 * for debug
	 * </pre>
	 * @return
	 */
	public static Map<String, Integer> getFailedFiltersMap(){
		return failedFiltersMap;
	}
	/**
	 * <pre>
	 * for debug
	 * check filter 후 실패한 마지막 필터 정보
	 * </pre>
	 * @return
	 */
	public static LottoFilter getJustExecutedFailedFilter() {
		return filterJustExecuted;
	}
	/**
	 * @param filters
	 * @param nums
	 * @return
	 */
	public static boolean isFilter(List<LottoFilter> filters, int[] nums) {
		for (LottoFilter f : filters) {
			if (f.filter(nums) == false) {
				filterJustExecuted = f;
				return false;
			}
		}
		return true;
	}

	/**
	 * @param filters
	 * @param vo
	 * @return
	 */
	public static boolean isFilter(List<LottoFilter> filters, NumberVO vo) {
		return isFilter(filters, vo.getNums6());
	}

	/**
	 * @param filters
	 * @param ivo
	 * @return
	 */
	public static boolean isFilter(List<LottoFilter> filters, NumberInfoVO ivo) {
		return isFilter(filters, ivo.getNums6());
	}

	/**
	 * @param filters
	 * @param name
	 * @return
	 */
	public static LottoFilter getFilter(List<LottoFilter> filters, String name) {
		for (LottoFilter filter : filters) {
			if (filter.getName().startsWith(name)) {
				return filter;
			}
		}
		return null;
	}

	/**
	 * 2 = 3 : 00.26% 3 = 3 : 00.26% 4 = 24 : 02.06% 5 = 38 : 03.26% 6 = 112 :
	 * 09.60% 7 = 162 : 13.88% 8 = 409 : 35.05% 9 = 200 : 17.14% 10 = 216 : 18.51%
	 * 산술적 복합성(AC)
	 * 
	 * @param nums
	 * @return
	 */
	public static int getAC(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 6; j++) {
				int ac = nums[j] - nums[i];
				set.add(ac);
			}
		}
		return (set.size() - 5);
	}

	/**
	 * 끝수 합
	 * 
	 * @param nums
	 * @return
	 */
	public static int getSumEnds(int[] nums) {
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += nums[i] % 10;
		}
		return sum;
	}

	/**
	 * 이웃 숫자들을 배역로 반환
	 * 
	 * @param nums
	 * @return
	 */
	public static int[] getNeighbors(int[] nums) {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < 6; i++) {
			int ln = nums[i] - 1;
			int rn = nums[i] + 1;
			if (ln < 1) {
				ln += 45;
			}
			if (45 < rn) {
				rn -= 45;
			}
			set.add(ln);
			set.add(rn);
		}
		return Arr.toIntArray(set);
	}

	/**
	 * load numbers from filename
	 * 
	 * @param filename
	 * @return List or null
	 */
	public static List<NumberVO> loadNumbers(String filename) {
		File file = new File(filename);
		return loadNumbers(file);
	}

	/**
	 * load numbers from filename
	 * 
	 * @param file
	 * @return List or null
	 */
	public static List<NumberVO> loadNumbers(File file) {
		if (!file.exists()) {
			LottoUtil.logger.debug("{} not found.!", file.getAbsolutePath());
			return null;
		}
		List<NumberVO> list = new ArrayList<>();
		List<String> numList = FileUtil.readAll(file);
		for (String line : numList) {
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
		return (0 < list.size()) ? list : null;
	}

	/**
	 * load from numbers file
	 * 
	 * @return
	 */
	public static List<NumberInfoVO> loadLottoList() {
		List<NumberInfoVO> list = new ArrayList<NumberInfoVO>();
		TextReader reader = null;
		try {
			reader = new TextReader(FILE_LOTTO_NUMBER);
			// ac L05 L10 au ma ha
			// 0640 2015-03-07 14-15-18-21-26-35 23 129 9 0 0 9 1,728,768,834 48 54,024,027
			// 1,639 1,582,156 85,410 50,000 1,404,905 5,000 7 1 1
			// 첫 줄은 컬럼명
			NUMBER_TITLE = reader.readLine();
			String line;
			while ((line = reader.readLine()) != null) {
				if (Str.isEmpty(line))
					continue;
				NumberInfoVO niv = new NumberInfoVO();
				String[] ls = line.split("\t");
				int idx = 0;
				niv.setRound(Integer.parseInt(ls[idx++]));
				niv.setDate(ls[idx++]);
				String ns = ls[idx++];
				String[] ss = ns.split("-");
				for (int i = 0; i < 6; i++) {
					niv.setNum(Integer.parseInt(ss[i]), i);
				}
				niv.setNum(Integer.parseInt(ls[idx++]), 6); // bonus
				idx++; // sum
				idx++; // ac
				niv.calcAc();
				niv.setLast05(Integer.parseInt(ls[idx++]));
				niv.setLast10(Integer.parseInt(ls[idx++]));
				// 1등 ~ 5등
				for (int i = 0; i < 5; i++) {
					int cnt = Integer.parseInt(ls[idx++].replace(",", ""));
					long money = Long.parseLong(ls[idx++].replace(",", ""));
					CountMoneyVO cm = new CountMoneyVO(cnt, money);
					niv.addCountMoney(cm);
				}
				// 자동, 수동, 반자동
				niv.setN1Auto(Integer.parseInt(ls[idx++]));
				niv.setN1Manu(Integer.parseInt(ls[idx++]));
				niv.setN1Half(Integer.parseInt(ls[idx++]));

				list.add(niv);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader = (TextReader) Obj.invokeCloseIfExists(reader);
		}
		return list;
	}
}
