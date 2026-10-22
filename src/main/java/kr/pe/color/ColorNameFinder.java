/**
 * ColorNameFinder.java
 */
package kr.pe.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
public class ColorNameFinder {

	// 1. 기준이 될 표준 색상 마스터 테이블 선언 (W3C/HTML 표준 일부 예시)
	private static final Map<String, Color> COLOR_MAP = new HashMap<>();

	static {
		COLOR_MAP.put("Magenta", new Color(255, 0, 255));
		COLOR_MAP.put("AliceBlue", new Color(240, 248, 255));
		COLOR_MAP.put("CadetBlue2", new Color(142, 229, 238));
		COLOR_MAP.put("DeepSkyBlue", new Color(0, 191, 255));
		COLOR_MAP.put("Black", new Color(0, 0, 0));
		COLOR_MAP.put("White", new Color(255, 255, 255));
		COLOR_MAP.put("Gray", new Color(128, 128, 128));
		COLOR_MAP.put("Red", new Color(255, 0, 0));
		COLOR_MAP.put("Blue", new Color(0, 0, 255));
	}

	// 2. HEX 코드로 이름을 찾는 메서드 (#FF00FF -> Magenta)
	public static String getColorNameFromHex(String hexCode) {
		try {
			Color targetColor = Color.decode(hexCode);
			return getColorNameFromRgb(targetColor.getRed(), targetColor.getGreen(), targetColor.getBlue());
		} catch (NumberFormatException e) {
			return "유효하지 않은 HEX 코드";
		}
	}

	// 3. RGB 값으로 이름을 찾는 핵심 메서드 (가장 가까운 색 근사치 연산)
	public static String getColorNameFromRgb(int r, int g, int b) {
		String closestColorName = "Unknown (알 수 없음)";
		double minDistance = Double.MAX_VALUE;

		// 마스터 맵을 순회하며 3차원 RGB 공간의 최단 거리를 계산
		for (Map.Entry<String, Color> entry : COLOR_MAP.entrySet()) {
			Color color = entry.getValue();

			// 유클리드 거리 공식 계산 (루트는 대소 비교에 불필요하므로 제곱 합산만 사용)
			double distance = Math.pow(color.getRed() - r, 2) + Math.pow(color.getGreen() - g, 2)
					+ Math.pow(color.getBlue() - b, 2);

			// 가장 오차가 적은(거리가 가까운) 색상 정보 갱신
			if (distance < minDistance) {
				minDistance = distance;
				closestColorName = entry.getKey();
			}
		}

		// 오차가 아예 없으면 정확한 매칭, 오차가 있다면 "가장 가까운 색상명"으로 표기
		if (minDistance == 0) {
			return closestColorName;
		} else {
			return closestColorName + " (근사치)";
		}
	}

	public static void main(String[] args) {
		// 테스트 1: 정확히 일치하는 경우
		System.out.println("#FF00FF 의 색상 이름: " + getColorNameFromHex("#FF00FF"));
		// 출력: Magenta

		// 테스트 2: 마스터 맵에는 없지만 사용자가 입력한 값과 오차가 가장 적은 근사치를 찾는 경우
		// (254, 2, 253)은 마젠타(255, 0, 255)와 가장 가깝습니다.
		System.out.println("RGB(254, 2, 253) 의 색상 이름: " + getColorNameFromRgb(254, 2, 253));
		// 출력: Magenta (근사치)
	}
}
