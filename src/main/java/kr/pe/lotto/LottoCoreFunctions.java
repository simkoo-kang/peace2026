/**
 * LottoCoreFunctions.java
 */
package kr.pe.lotto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.lotto.filter.LottoFilter;
import kr.pe.lotto.filter.bool.EqualIntervalBooleanFilter;
import kr.pe.lotto.filter.bool.SteppingStoneBooleanFilter;
import kr.pe.lotto.filter.mm.ACMinFilter;
import kr.pe.lotto.filter.mm.ConsecutivePairsMaxFilter;
import kr.pe.lotto.filter.mm.FirstNumberMaxFilter;
import kr.pe.lotto.filter.mm.Last10MinFilter;
import kr.pe.lotto.filter.mm.LastNumberMinFilter;
import kr.pe.lotto.filter.mm.WinningRankGroupMaxFilter;
import kr.pe.lotto.filter.range.EvenRangeFilter;
import kr.pe.lotto.filter.range.HLineRangeFilter;
import kr.pe.lotto.filter.range.HighRangeFilter;
import kr.pe.lotto.filter.range.MatchCountRangeFilter;
import kr.pe.lotto.filter.range.VLineRangeFilter;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.util.Arr;
import kr.pe.util.Coll;
import kr.pe.util.Str;

/**
 * <pre>
 * 
 * PC  2026. 8. 30. 오후 4:12:49
 * </pre>
 */
public class LottoCoreFunctions {

	private static Logger logger = LoggerFactory.getLogger(LottoCoreFunctions.class);

	private static LottoCoreFunctions instance = null;

	/**
	 * <pre>
	 * 싱글톤 생성
	 * </pre>
	 * 
	 * @param lottoList
	 * @return
	 */
	public static LottoCoreFunctions getInstance(List<NumberInfoVO> lottoList) {
		if (instance == null) {
			instance = new LottoCoreFunctions(lottoList);
		}
		return instance;
	}

	/**
	 * The winning numbers so far 현재까지 당첨 번호
	 */
	private List<NumberInfoVO> lottoList;
	private List<LottoFilter> filters;
	private Map<String, LottoFilter> filterMap;

	private NumberInfoVO lastVo;

	/**
	 * <pre></pre>
	 * 
	 * @param lottoList
	 */
	public LottoCoreFunctions(List<NumberInfoVO> lottoList) {
		this.lottoList = lottoList;
		logger.debug("lotto list {}개 -----", lottoList.size());

		// filter list
		this.filters = getBaseFilters();

		this.getLastVo();

//		this.appendFilters();

		logger.debug("base lotto filters {}개 -----", this.filters.size());

		// filter map
		this.filterMap = new HashMap<>();

		int filterIndex = 0;
		for (LottoFilter f : this.filters) {
			String name = f.getName();
			f.setName(name + "_" + Str.fill0(++filterIndex));
			this.filterMap.put(f.getName(), f);
		}
	}

	/**
	 * <pre>
	 * 필터 추가
	 * 
	 * PC 2026. 9. 2. 오후 7:22:01
	 * </pre>
	 *
	 */
	protected void appendFilters() {
		int[] last10 = this.getNumbersFromLast(10);
		logger.debug("last10: {} {}", last10.length, Arrays.toString(last10));

		this.filters.add(0, new Last10MinFilter("Last10", 4, last10));

		int[] last5 = this.getNumbersFromLast(5);
		this.filters.add(new MatchCountRangeFilter("Last5", 2, 5, last5));

		this.filters.add(new MatchCountRangeFilter("이월수", 0, 2, this.lastVo.getNums6()));
		this.filters.add(new MatchCountRangeFilter("이웃수", 0, 3, this.lastVo.getNums6()));

		/**
		 * <pre>
		 * 출현 빈도수 별 그룹을 생성하는 클래스입니다.
		 * 1. groupA, groupB, groupC, panelA, panelB, panelC 리스트에 각 각 max 개 이상 있으면 차단
		 * 2. groupB에 0개 이면 차단, 즉 groupA, groupC에 6개란 뜻
		 * </pre>
		 */
		this.filters.add(new WinningRankGroupMaxFilter("빈도수 그룹", 4, this.lottoList));

//		for (int idx = 0; idx < FrogRangeFilter.maxCount; idx++) {
//			this.filters.add(new FrogRangeFilter("개구리" + (idx + 1), 2, 4, idx));
//		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 2. 오후 5:11:00
	 * </pre>
	 *
	 * @return
	 */
	public NumberInfoVO getLastVo() {
		if (this.lastVo == null) {
			this.lastVo = this.lottoList.get(this.lottoList.size() - 1);
		}
		return this.lastVo;
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 8. 30. 오후 4:16:15
	 * </pre>
	 *
	 * @return
	 */
	public static List<LottoFilter> getBaseFilters() {

		List<LottoFilter> filters = new ArrayList<>();
		filters.add(new ACMinFilter("AC", 6)); // 6이상
		// 마지막 번호는 >= 32
		filters.add(new LastNumberMinFilter("마지막 번호", 32));

		// 첫 번째 번호는 <=14
		filters.add(new FirstNumberMaxFilter("첫 번째 번호", 14));
		// [1,2] 형태는 2개 미만, [1,2,3] 차단
		filters.add(new ConsecutivePairsMaxFilter("연번", 1));

		/**
		 * interval 이 없으면 모든 동일 간격 1-2-3, 1-3-5, 2-5-8, .... 촤우 대각선 포함. --------
		 */
//		filters.add(new EqualIntervalBooleanFilter("동일간격", false));
		// [1,3,5], [2,6,10], ... 있으면 차단 즉 있으면 true, 없으면 false
		filters.add(new EqualIntervalBooleanFilter("동일간격", false, 2));
		// [6,12,18], [2,8,14] 대각선 형식 차단
		filters.add(new EqualIntervalBooleanFilter("대각선⬋", false, 6));
		// [1,9,17], [2,10,18] 대각선 형식 차단
		filters.add(new EqualIntervalBooleanFilter("대각선⬊", false, 8));
		// '1,2,4' 또는 '1,3,4' 처럼 3개 숫자가 징검다리 형태 차단
		// --------------------------------------------------------------------------
		filters.add(new SteppingStoneBooleanFilter("징금다리", false));

//		filters.add(new SumRangeFilter("Sum", 100, 175));

		filters.add(new EvenRangeFilter("Even", 2, 4));
		filters.add(new HighRangeFilter("High", 2, 4));

		// 라인 수는 3-5개, 한 줄에 최대 3개
		filters.add(new HLineRangeFilter("HLines", 3, 5, 3));
		// 라인 수는 3-5개, 한 줄에 최대 3개
		filters.add(new VLineRangeFilter("VLines", 3, 5, 3));

		// 동일 끝수 1개(모든 끝수가 다른 경우) 또는 2개(한 개가 같은 경우)
//		filters.add(new SameEndingRangeFilter("동일끝수", 1, 2));
		// 끝수 합계가 지정한 범위(12~38) 안인지 검사합니다.
//		filters.add(new SumEndingRangeFilter("끝수합", 12, 38));
		// 번호대별 최대 수량 <= 3, 전멸 구간 <= 2
//		filters.add(new NumberZoneCountMaxFilter("번호대", 3, 2));

		// 5구간, 9구간 - 최소 3-5구간, max 3개
//		filters.add(new SectionRangeFilter("5구간", 3, 5, 3, 9));
//		filters.add(new SectionRangeFilter("9구간", 4, 6, 3, 5));

		// [ 1, 2, 6, 7, 8, 9, 13, 14, 29, 30, 34, 35, 36, 37, 41, 42, 43, 44, 45 ]
//		filters.add(new CornerCountRangeFilter("모서리", 1, 4));
//		for (int idx = 0; idx < FondantMaxFilter.maxCount; idx++) {
//			filters.add(new FondantMaxFilter("퐁당퐁당" + (idx + 1), 6, idx));
//		}

//		filters.add(new MultipleRangeFilter("3의 배수", 0, 3, 3));
//		filters.add(new MultipleRangeFilter("5의 배수", 0, 3, 5));

//		filters.add(new PrimeRangeFilter("솟수", 0, 3));

//		filters.add(new PairsDdangRangeFilter("쌍수", 0, 1, 0));
//		filters.add(new PairsDdangRangeFilter("광땡", 0, 1, 1));

//		filters.add(new CompositionRangeFilter("합성수", 0, 3));

//		for (int idx = 0; idx < NeverRangeFilter.maxCount; idx++) {
//			filters.add(new NeverRangeFilter("Never" + (idx + 1), 1, 5, idx));
//		}

//		for (int idx = 0; idx < TriangleMaxFilter.maxCount; idx++) {
//			filters.add(new TriangleMaxFilter("삼각패턴" + (idx + 1), 6, idx));
//		}

		return filters;
	}

	/**
	 * <pre>
	 * 현재 20회차
	 * 끝에서 부터 rounds 회차 만큼 출현 번도들 - 중복 제거
	 * lastIn : true  11 - 20회차까지 출현 번호들
	 * </pre>
	 * 
	 * @param rounds
	 * @return
	 */
	public int[] getNumbersFromLast(int rounds) {
		return getNumbersFromLast(rounds, true);
	}

	/**
	 * <pre>
	 * 현재 20회차
	 * 끝에서 부터 rounds 회차 만큼 출현 번도들 - 중복 제거
	 * lastIn : true  11 - 20회차까지 출현 번호들
	 * lastIn: false  10 - 19회차까지 출현 번호들
	 * </pre>
	 * 
	 * @param rounds
	 * @param lastIn
	 * @return
	 */
	public int[] getNumbersFromLast(int rounds, boolean lastIn) {
		int size = this.lottoList.size() - ((lastIn) ? 0 : 1);
		return getNumbers(size - rounds, size);
	}

	/**
	 * <pre>
	 * i=startRound; i<endRound
	 * 이 구간이 출현 번호들 - 중복 제거
	 * </pre>
	 * 
	 * @param startRound
	 * @param endRound
	 * @return
	 */
	public int[] getNumbers(int startRound, int endRound) {
		Set<Integer> set = new HashSet<>();
		for (int r = startRound; r < endRound; r++) {
			NumberInfoVO ivo = this.lottoList.get(r);
			int[] nums = ivo.getNums();
			for (int i = 0; i < 6; i++) {
				set.add(nums[i]);
			}
		}
		return Arr.toIntArray(set);
	}

	/**
	 * <pre>
	 * size = lottoList.size() - 1
	 * startRound = size - rounds, endRound = size
	 * 즉 현재 20회차 라고 하면,
	 *  lastIn: true  11 - 20회차 출현 번호 빈도수
	 * 이 구간이 출현 번호들 빈도수
	 * 
	 * PC 2026. 9. 4. 오전 8:32:45
	 * </pre>
	 *
	 * @param rounds
	 * @return
	 */
	public Map<Integer, Integer> getNumbersFrequencyMapFromLast(int rounds) {
		return getNumbersFrequencyMapFromLast(rounds, true);
	}

	/**
	 * <pre>
	 * size = lottoList.size() - 1
	 * startRound = size - rounds, endRound = size
	 * 즉 현재 20회차 라고 하면,
	 *  lastIn: true  11 - 20회차 출현 번호 빈도수
	 *  lastIn: false 10 - 19회차 출현 번호 빈도수
	 * 이 구간이 출현 번호들 빈도수
	 * 
	 * PC 2026. 9. 4. 오전 8:32:45
	 * </pre>
	 *
	 * @param rounds
	 * @param lastIn
	 * @return
	 */
	public Map<Integer, Integer> getNumbersFrequencyMapFromLast(int rounds, boolean lastIn) {
		int size = this.lottoList.size() - ((lastIn) ? 0 : 1);
		return getNumbersFrequencyMap(size - rounds, size);
	}

	/**
	 * <pre>
	 * i=startRound; i<endRound
	 * 이 구간이 출현 번호들 빈도수
	 * </pre>
	 * 
	 * @param startRound
	 * @param endRound
	 * @return
	 */
	public Map<Integer, Integer> getNumbersFrequencyMap(int startRound, int endRound) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int r = startRound; r < endRound; r++) {
			NumberInfoVO ivo = this.lottoList.get(r);
			int[] nums = ivo.getNums();
			for (int i = 0; i < 6; i++) {
				Coll.addValue(map, nums[i]);
			}
		}
		return map;
	}

	/**
	 * @return the lottoList
	 */
	public List<NumberInfoVO> getLottoList() {
		return this.lottoList;
	}

	/**
	 * @return the filters
	 */
	public List<LottoFilter> getFilters() {
		return this.filters;
	}

	/**
	 * <pre>
	 * 필터 추가, 이미 있으면 지우고 다시 추가
	 * </pre>
	 *
	 * @param f
	 */
	public void addFilter(LottoFilter f) {
		LottoFilter filter = this.filterMap.get(f.getName());
		if (filter == null) {
			this.filters.add(f);
		}
		this.filterMap.put(f.getName(), f);
	}

	/**
	 * <pre>
	 * 필터 추가, 이미 있으면 지우고 다시 추가 - index 위치에 추가
	 * </pre>
	 *
	 * @param f
	 */
	public void addFilter(int index, LottoFilter f) {
		LottoFilter filter = this.filterMap.get(f.getName());
		if (filter == null) {
			this.filters.add(index, f);
		}
		this.filterMap.put(f.getName(), f);
	}
}
