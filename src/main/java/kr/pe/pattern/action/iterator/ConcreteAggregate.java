/**
 * ConcreateAggregate.java
 */
package kr.pe.pattern.action.iterator;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오후 6:47:47
 * </pre>
 */
public class ConcreteAggregate implements Aggregate {
	private Object[] objs;
	private int index;

	/**
	 * <pre></pre>
	 * 
	 */
	public ConcreteAggregate(int size) {
		super();
		this.objs = new Object[size];
		this.index = 0;
	}

	public boolean add(Object o) {
		if (this.index < this.objs.length) {
			this.objs[this.index++] = o;
			return true;
		}
		return false;
	}

	@Override
	public Iterator iterator() {
		return new ConcreteIterator(this.objs);
	}
}
