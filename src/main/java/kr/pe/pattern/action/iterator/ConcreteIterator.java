/**
 * ConcreteIterator.java
 */
package kr.pe.pattern.action.iterator;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:54:50
 * </pre>
 */
public class ConcreteIterator implements Iterator {

	private Object[] objs;
	private int nextIndex;

	/**
	 * <pre>
	 * 생성자로 순회할 컬렉션을 받아 필드에 참조 시킴
	 * </pre>
	 * 
	 * @param objs
	 */
	public ConcreteIterator(Object[] objs) {
		super();
		this.objs = objs;
		this.nextIndex = 0;
	}

	/**
	 * 순회할 다음 요소가 있는지 true / false
	 */
	@Override
	public boolean hasNext() {
		return this.nextIndex < this.objs.length;
	}

	/**
	 * 다음 요소를 반환하고 커서를 증가시켜 다음 요소를 바라보도록 한다.
	 */
	@Override
	public Object next() {
		return this.objs[this.nextIndex++];
	}

}
