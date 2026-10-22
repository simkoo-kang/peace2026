/**
 * ColorTables.java
 */
package kr.pe.color;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import kr.pe.color.base.ColorItem;
import kr.pe.color.base.ColorTable;
import kr.pe.color.base.Colors;
import kr.pe.util.LogUtil;
import kr.pe.util.io.FileUtil;
import kr.pe.util.xml.Jaxb;

/**
 * <pre></pre>
 * 
 * gbrai 2026. 10. 17.
 */
public class ColorTables {

	private Logger logger;
	private Map<String, ColorTable> colorMap;

	/**
	 * <pre></pre>
	 */
	public ColorTables() {
		super();
		this.logger = LogUtil.getLogger(this);
		this.colorMap = new HashMap<String, ColorTable>();

		this.logger.debug(this.getClass().getName());
	}

	public void clear() {
		this.colorMap.clear();
	}

	public void setColors(Colors colors) {
		this.colorMap.clear();
		// 파싱된 데이터 검증 출력
		for (ColorTable table : colors.getTables()) {
			this.colorMap.put(table.getId(), table);
		}
	}

	/**
	 * <pre></pre>
	 * 
	 * @param group
	 * @param line
	 * @return
	 */
	public ColorItem addColor(String id, String line) {
		ColorTable ct = this.colorMap.get(id);
		if (ct == null) {
			ct = new ColorTable();
			this.colorMap.put(id, ct);
		}
		return ct.addColor(line);
	}

	/**
	 * @return the colorMap
	 */
	public Map<String, ColorTable> getColorMap() {
		return this.colorMap;
	}

	/**
	 * <pre></pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		String htmlfilename = FileUtil.PATH_ECLIPSE + "/peace2026/conf/color_table.html";
		String xmlfilename = FileUtil.PATH_WORKSPACE_DOCS + "/color_table.xml";

//		String xmlData = "<colors>" + "  <table id=\"gray\">"
//				+ "    <color name=\"gray96\" hex=\"#F5F5F5\" rgb=\"245,245,245\" />"
//				+ "    <color name=\"gray82\" hex=\"#D1D1D1\" rgb=\"209,209,209\" />" + "  </table>"
//				+ "  <table id=\"blue\">" + "    <color name=\"AliceBlue\" hex=\"#F0F8FF\" rgb=\"240,248,255\" />"
//				+ "    <color name=\"CadetBlue2\" hex=\"#8EE5EE\" rgb=\"142,229,238\" />"
//				+ "    <color name=\"DeepSkyBlue\" hex=\"#00BFFF\" rgb=\"0,191,255\" />" + "  </table>" + "</colors>";
//
//		Colors colors = Jaxb.convertToObject(xmlData, Colors.class);
//		Jaxb.convertToFile(colors, xmlfilename);

		// JAXB 컨텍스트 및 언마셜러 초기화
		Colors colors = Jaxb.convertFromFile(xmlfilename, Colors.class);

		// 파싱된 데이터 검증 출력
		for (ColorTable table : colors.getTables()) {
			System.out.println("Table ID: " + table.getId());
			for (ColorItem color : table.getColors()) {
				System.out.println(color.toString());
			}
		}

		/**
		 * 파싱 html to xml, save to xmlfilename -------------------------------------
		 */
//		colors.clear();
//		Html html = new Html(new File(htmlfilename));
//		List<String> list = new ArrayList<String>();
//		Element el = html.getSelectFirst("ul.np");
//		if (el != null) {
//			Iter it = new StrIter();
//			it.find(el.text());
//			while (it.hasMore()) {
//				String next = it.next();
//				list.add(next);
//				System.out.println(next);
//			}
//			for (String color : list) {
//				ColorTable ct = new ColorTable(color);
//				colors.addTable(ct);
//				String selector = "table.colortable[summary*=" + color + "] tbody tr";
//				Elements rows = html.getDocument().select(selector);
//				System.out.println(color + " = " + rows.size());
//				for (Element row : rows) {
//					ct.addColor(row.text());
//					System.out.println(row.text());
//				}
//			}
//			Jaxb.convertToFile(colors, xmlfilename);
//		}
	}
}
