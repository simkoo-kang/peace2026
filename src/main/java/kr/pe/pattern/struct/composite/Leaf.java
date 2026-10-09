/**
 * Leaf.java
 */
package kr.pe.pattern.struct.composite;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:00:12
 * </pre>
 */
public class Leaf implements CompositeComponent {

	@Override
	public void operation() {
		LogUtil.debugAll(this, "{} 호출", this.getClass().getName());
	}

}
