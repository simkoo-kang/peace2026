/**
 * Composite.java
 */
package kr.pe.pattern.struct.composite;

import java.util.ArrayList;
import java.util.List;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:02:59
 * </pre>
 */
public class Composite implements CompositeComponent {

	// Leaf 와 Composite 객체 모두를 저장하여 관리하는 내부 리스트
	List<CompositeComponent> components = new ArrayList<>();

	public void add(CompositeComponent c) {
		this.components.add(c); // 리스트 추가
	}

	public void remove(CompositeComponent c) {
		this.components.remove(c); // 리스트 삭제
	}

	@Override
	public void operation() {
		LogUtil.debugAll(this, "{} 호출", this.getClass().getName());

		// 내부 리스트를 순회하여, 단일 Leaf이면 값을 출력하고,
		// 또다른 서브 복합 객체이면, 다시 그 내부를 순회하는 재귀 함수 동작이 된다.
		for (CompositeComponent component : this.components) {
			component.operation(); // 자기 자신을 호출(재귀)
		}
	}

	public List<CompositeComponent> getChild() {
		return this.components;
	}
}
