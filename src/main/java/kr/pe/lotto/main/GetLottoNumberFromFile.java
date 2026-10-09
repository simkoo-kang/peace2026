/**
 * 
 */
package kr.pe.lotto.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.pe.lotto.LottoMain;
import kr.pe.lotto.util.LottoUtil;
import kr.pe.lotto.vo.CountMoneyVO;
import kr.pe.lotto.vo.NumberInfoVO;
import kr.pe.util.Arr;
import kr.pe.util.date.CDate;
import kr.pe.util.io.FileUtil;
import kr.pe.util.iter.Iter;
import kr.pe.util.iter.NumIter;

/**
 * CREATE TABLE `lotto`.`tb_numbers` ( `round` INT(5) NOT NULL DEFAULT 0 COMMENT
 * 'round', `lt_date` VARCHAR(10) NOT NULL DEFAULT 'left(now(),10)' COMMENT
 * 'lotted date', `n1` INT(2) NOT NULL DEFAULT 0 COMMENT 'number 1', `n2` INT(2)
 * NOT NULL DEFAULT 0 COMMENT 'number 2', `n3` INT(2) NOT NULL DEFAULT 0 COMMENT
 * 'number 3', `n4` INT(2) NOT NULL DEFAULT 0 COMMENT 'number 4', `n5` INT(2)
 * NOT NULL DEFAULT 0 COMMENT 'number 5', `n6` INT(2) NOT NULL DEFAULT 0 COMMENT
 * 'number 6', `bonus` INT(2) NOT NULL DEFAULT 0 COMMENT 'bonus number', `total`
 * INT(3) ZEROFILL NULL DEFAULT 0 COMMENT 'sum of numbers', `ac` INT(2) ZEROFILL
 * NULL DEFAULT 0 COMMENT 'ac value', `L05` INT(1) ZEROFILL NULL DEFAULT 0
 * COMMENT 'last 05 count', `L10` INT(1) ZEROFILL NULL DEFAULT 0 COMMENT 'last
 * 10 count', `n1_count` INT(5) ZEROFILL NULL DEFAULT 0 COMMENT 'first rank
 * count', `n1_amount` INT(11) ZEROFILL NULL DEFAULT 0 COMMENT 'first rank
 * amount', `n2_count` INT(5) ZEROFILL NULL DEFAULT 0 COMMENT 'second rank
 * count', `n2_amount` INT(11) ZEROFILL NULL DEFAULT 0 COMMENT 'second rank
 * amount', `n3_count` INT(5) ZEROFILL NULL DEFAULT 0 COMMENT 'third rank
 * count', `n3_amount` INT(11) ZEROFILL NULL DEFAULT 0 COMMENT 'third rank
 * amount', `n4_count` INT(8) ZEROFILL NULL DEFAULT 0 COMMENT 'fourth rank
 * count', `n4_amount` INT(6) ZEROFILL NULL DEFAULT 0 COMMENT 'fourth rank
 * amount', `n5_count` INT(8) ZEROFILL NULL DEFAULT 0 COMMENT 'fifth rank
 * count', `n5_amount` INT(5) ZEROFILL NULL DEFAULT 0 COMMENT 'fifth rank
 * amount', `n1_auto` INT(2) ZEROFILL NULL DEFAULT 0 COMMENT 'first rank auto
 * count', `n1_manu` INT(2) ZEROFILL NULL DEFAULT 0 COMMENT 'first rank manu
 * count', `n1_half` INT(2) ZEROFILL NULL DEFAULT 0 COMMENT 'first rank half
 * count', PRIMARY KEY (`round`), UNIQUE INDEX `lt_date_UNIQUE` (`lt_date` ASC)
 * VISIBLE ) COMMENT = 'lotto numbers';
 */
public class GetLottoNumberFromFile extends LottoMain {

	/**
	 * round, date, lotto numbers and bonus number
	 */
//	public final static String XPATH_NUMBER_INFO = "/html/body/div[3]/div[3]/div/div[2]/div/div[1]/div[2]/div";
	public final static String XPATH_NUMBER_INFO = "/html/body/div[4]/div[3]/div/div[2]/div/div[1]/div[2]/div";
	/**
	 * rank, the number of winners and Winning amount
	 */
//	public final static String XPATH_MONEY_INFO = "/html/body/div[3]/div[3]/div/div[3]/div[2]";
	public final static String XPATH_MONEY_INFO = "/html/body/div[4]/div[3]/div/div[3]/div[2]/div[2]";

	/**
	 * 
	 */
	public GetLottoNumberFromFile() {
		super();
	}

	/**
	 * get the [roubd]th lotto number
	 * 
	 * @param round
	 */
	public NumberInfoVO execute(int round) {

		String htmlFilename = FileUtil.DIR_DEFAULT_CONF + "/lotto/html/" + round + ".html";
		File file = new File(htmlFilename);

		this.logger.debug("{} size is {}", htmlFilename, FileUtil.existFile(file));
		if (!FileUtil.existFile(file)) {
			this.logger.debug("{} is not found!", htmlFilename);
			return null;
		}

		Document doc;
		try {
			// 방법 A: InputStream을 이용해 크기 제한 없이 파싱하기 (추천)
			FileInputStream fis = new FileInputStream(file);
			doc = Jsoup.parse(fis, "UTF-8", "");

			NumberInfoVO ivo = new NumberInfoVO();
			Iter it = new NumIter();

			String text;
			Elements els = doc.selectXpath(XPATH_NUMBER_INFO);
			if (els == null) {
				this.logger.debug("elements is null");
			} else {
				this.logger.debug(els.text()); // 제 1240회 추첨 결과 2026.09.05 추첨 당첨번호 11 13 19 20 31 44 보너스번호 27 당첨번호 보너스번호
				text = els.text();
				if (it.find(text)) {
					int idx = 0;
					ivo.setRound(Integer.parseInt(it.next()));
					String date = it.next() + "-" + it.next() + "-" + it.next();
					ivo.setDate(date);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++);
					ivo.setNum(Integer.parseInt(it.next()), idx++); // 보너스

					ivo.calcAc();
					int[] nums = ivo.getNums6();
					System.out.println(Arr.toStringIntArray(nums));

					int[] last = this.getNumbersFromLast(5);
					System.out.println(Arr.toStringIntArray(last));
					ivo.setLast05(Arr.contains(last, nums));

					last = this.getNumbersFromLast(10);
					System.out.println(Arr.toStringIntArray(last));
					ivo.setLast10(Arr.contains(last, nums));
					System.out.println(ivo.getLast05() + " , " + ivo.getLast10());

					this.logger.debug(ivo.toStringSum());
					return null;
				} else {
					this.logger.debug("no matched!");
				}
			}
			/**
			 * 당첨자 수, 당첨 금액
			 */
			Elements moneyEls = doc.selectXpath(XPATH_MONEY_INFO);
			if (moneyEls == null) {
				this.logger.debug("elements is null");
			} else {
				this.logger.debug(moneyEls.text()); // 1등 28,669,084,128원 16 1,791,817,758원 6개번호 일치 2등 4,778,180,706원 78
													// 61,258,727원 5개번호 일치 + 보너스번호 일치 3등 4,778,182,080원 3,087 1,547,840원
													// 5개번호 일치 4등 7,760,100,000원 155,202 50,000원 4개번호 일치 5등
													// 13,097,115,000원 2,619,423 5,000원 3개번호 일치
				text = moneyEls.text().trim().replace(",", "");
				this.logger.debug(text);
				if (it.find(text)) {
					for (int rank = 1; rank < 6; rank++) {
						this.logger.debug(it.matchStringAll());
						CountMoneyVO cmo = new CountMoneyVO();
						String nc = null, nm = null;
						this.logger.debug(it.next()); // skip 1등
						this.logger.debug(it.next()); // skip 1등 총 상금액
						nc = it.next(); // 담첨자수
						this.logger.debug("nc={}", nc);
						nm = it.next(); // 당첨 금액
						this.logger.debug("nm={}", nm);
						this.logger.debug(it.next()); // skip 6개번호 일치
						cmo.setCount(Integer.parseInt(nc));
						cmo.setMoney(Long.parseLong(nm));
						ivo.addCountMoney(cmo);
					}
				}
			}
			return ivo;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 안가져온 번호를 모두 가져온다.
	 */
	public boolean execute() {
		int round;
		CDate curdate;
		NumberInfoVO lastVo = this.getLastVo();
		if (lastVo.getRound() == 0) {
			round = 1;
			curdate = new CDate(LottoUtil.FIRST_LOTTO_DATE);
		} else {
			round = lastVo.getRound() + 1;
			CDate lastDate = new CDate(lastVo.getDate());
			curdate = lastDate.addDay(7);
		}
		CDate today = new CDate();
		if (!curdate.before(today)) {
			this.logger.debug("It's still before the draw.(아직 추첨 전입니다.)");
			String lotterydate = curdate.getDateShortString();
			this.logger.debug(
					"The lottery date and time is " + lotterydate + " at 8 p.m.(추첨일시은 " + lotterydate + " 오후 8시입니다.)");
			return false;
		}
		while (curdate.before(today)) {
			NumberInfoVO niv = execute(round++);

			if (niv == null) {
				break;
			}
			addLottoNumber(niv);

			this.logger.debug(niv.toString());
			try {
				FileUtil.writeln(LottoUtil.FILE_LOTTO_NUMBER, niv.toString(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			curdate.addDay(7);
		}
		this.logger.debug("completed!");
		return true;
	}

	/**
	 * @param niv
	 */
	protected void addLottoNumber(NumberInfoVO niv) {
		this.lottoList.add(niv);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetLottoNumberFromFile gln = new GetLottoNumberFromFile();
		gln.execute();
//		int[][] nums = { { 11, 13, 19, 20, 31, 44 }, { 07, 13, 16, 23, 24, 43 }, { 02, 04, 10, 16, 31, 41 } };
//		for (int i = 0; i < nums.length; i++) {
//			System.out.println(LottoUtil.getAC(nums[i]));
//		}
//
//		int[] ln = gln.getLastVo().getNums6();
//		int[] last = gln.getNumbersFromLast(10);
//		System.out.println(Arr.toStringIntArray(ln));
//		System.out.println(Arr.toStringIntArray(last));
//		System.out.println(Arr.contains(last, ln));
	}

}
