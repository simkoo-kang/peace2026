/**
 * 
 */
package kr.pe.lotto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.pe.lotto.util.LottoUtil;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.lotto.vo.NumberVO;
import kr.pe.util.Arr;
import kr.pe.util.Str;
import kr.pe.util.io.FileUtil;

/**
 * <pre>
 * </pre>
 */
public abstract class LottoMain {

	protected Logger logger;

	/**
	 * The winning numbers so far 현재까지 당첨 번호
	 */
	protected List<NumberInfoVO> lottoList;
	/**
	 * 1st place number and 2nd place number won so far - it's hard to come out
	 * again. 지금까지 당첨된 1등 번호와 2등 번호 - 다시 나오기 어렵다.
	 */
	protected List<String> cache;

	protected Random rand;

	/**
	 * LOW_SUM = 83, HIGH_SUM = 196, MAX_FIRST = 18
	 */
	public LottoMain() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.lottoList = LottoUtil.loadLottoList();
		this.cache = new ArrayList<String>();

		this.rand = new Random(new Date().getTime());

		for (NumberInfoVO niv : this.lottoList) {
			NumberVO vo = niv.getNumberVO();
			// 1등 번호
			this.cache.add(vo.toString());
			// 2등 번호
			int[] nums = vo.getNums();
			for (int i = 0; i < 6; i++) {
				this.cache.add(secondNumber(nums, i));
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Integer> set = Set.of(1, 2, 3, 4);
		System.out.println(set.toString());
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
	 * @param nums
	 * @param idx
	 * @return
	 */
	private String secondNumber(int[] nums, int idx) {
		int[] ns = Arrays.copyOf(nums, 6);
		ns[idx] = nums[6];
		Arrays.sort(ns);
		return new NumberVO(ns).toString();
	}

	/**
	 * The last one. If not, empty NumberInfoVO
	 * 
	 * @return
	 */
	public NumberInfoVO getLastVo() {
		if (this.lottoList.size() > 0) {
			return this.lottoList.get(this.lottoList.size() - 1);
		}
		return null;
	}

	/**
	 * @param filename
	 * @param lastnumbers
	 * @param append
	 */
	protected void toSave(String filename, List<NumberVO> lastnumbers, boolean append) {
		String lf = Str.LF;
		StringBuilder sb = new StringBuilder();
		for (NumberVO vo : lastnumbers) {
			sb.append(lf).append(vo);
		}
		toSave(filename, sb.toString(), append);
	}

	/**
	 * @param file
	 * @param lastnumbers
	 * @param append
	 */
	protected void toSave(File file, List<NumberVO> lastnumbers, boolean append) {
		String lf = Str.LF;
		StringBuilder sb = new StringBuilder();
		for (NumberVO vo : lastnumbers) {
			sb.append(lf).append(vo);
		}
		toSave(file, sb.toString(), append);
	}

	/**
	 * @param filename
	 * @param content
	 * @param append
	 */
	protected void toSave(String filename, String content, boolean append) {
		try {
			FileUtil.write(filename, content, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 * @param content
	 * @param append
	 */
	protected void toSave(File file, String content, boolean append) {
		try {
			FileUtil.write(file, content, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
