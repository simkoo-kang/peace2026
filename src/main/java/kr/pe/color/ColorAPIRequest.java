/**
 * ColorAPIRequest.java
 */
package kr.pe.color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 18.
 */
public class ColorAPIRequest {

	public static String fetchColorName(String hexCode) {
		// 1. 공백 제거 및 대문자 변환
		hexCode = hexCode.trim().toUpperCase();

		// 앞에 붙은 # 기호 제거
		if (hexCode.startsWith("#")) {
			hexCode = hexCode.substring(1);
		}
		// 3. 16진수 3자리 또는 6자리 정규식 검증
		if (!hexCode.matches("^[0-9A-F]{3,6}$")) {
			return "잘못된 HEX 형식";
		}

		try {
			String encodedHex = URLEncoder.encode(hexCode, "UTF-8");
			String urlString = "https://www.thecolorapi.com/id?hex=" + encodedHex;

			URL url = URI.create(urlString).toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 4. HTTP 헤더 설정 (⭐ 404 차단 해결을 위한 핵심 보안 패치)
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			// 자바 기본 연결 인터페이스를 일반 브라우저(크롬 등)처럼 인식하도록 세팅합니다.
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");

			conn.setConnectTimeout(4000); // 네트워크 타임아웃 4초 설정
			conn.setReadTimeout(4000);

			// 5. 서버 응답 코드 점검
			if (conn.getResponseCode() != 200) {
				return "API 에러 (" + conn.getResponseCode() + ")";
			}

			// 6. 데이터 스트림 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			conn.disconnect();

			// JSON 텍스트 정규식 파싱
			String jsonText = response.toString();
			System.out.println(jsonText);
			Pattern pattern = Pattern.compile("\"name\"\\s*:\\s*\\{\\s*\"value\"\\s*:\\s*\"([^\"]+)\"");
			Matcher matcher = pattern.matcher(jsonText);

			String value = "";
			if (matcher.find()) {
				value = matcher.group(1);
			}

			pattern = Pattern.compile("\"closest_named_hex\"\\s*:\\s*\"([^\"]+)\"");
			matcher = pattern.matcher(jsonText);

			if (matcher.find()) {
				value += "\t" + matcher.group(1);
				return value;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "네트워크 오류";
		}
		return "알 수 없는 색상";
	}

	public static void main(String[] args) {
		/*
		 * /id?hex=ffa or /id?hex=00ffa6 /id?rgb=rgb(255,0,0) or /id?rgb=20,43,55 Same
		 * goes for cmyk, hsl, and hsv formats
		 */
		String hex = "FA4AD3";
		hex = "BC8F8F";
		String color = ColorAPIRequest.fetchColorName(hex);
		System.out.println(color);
	}
}
