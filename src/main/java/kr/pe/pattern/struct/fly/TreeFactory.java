/**
 * TreeFactory.java
 */
package kr.pe.pattern.struct.fly;

import java.util.HashMap;
import java.util.Map;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 10. 오후 8:13:06
 * </pre>
 */
public class TreeFactory {

	private static final Map<String, TreeModel> treeTypes = new HashMap<>();

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 8:17:33
	 * </pre>
	 *
	 * @param mesh
	 * @param texture
	 * @return
	 */
	public static TreeModel getTreeModel(String mesh, String texture) {
		String key = mesh + "_" + texture;

		// 캐시에 없으면 새로 생성하고, 있으면 기존 객체 재사용
		if (!treeTypes.containsKey(key)) {
			treeTypes.put(key, new TreeModel(mesh, texture));
			LogUtil.debugAll(TreeFactory.class, "== 새로운 공유 객체 생성: " + key + " ==");
		}
		return treeTypes.get(key);
	}
}
