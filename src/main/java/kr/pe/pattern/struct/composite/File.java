/**
 * File.java
 */
package kr.pe.pattern.struct.composite;

import java.util.List;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:24:18
 * </pre>
 */
public class File extends FileNode {

	private int size; // 파일 사이즈

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 * @param size
	 */
	public File(String name, int size) {
		super(name);
		this.size = size;
	}

	@Override
	public List<Node> getFiles() {
		return null;
	}

	@Override
	public boolean add(Node node) {
		return false;
	}

	@Override
	public void sort() {
	}

	@Override
	public void sortReverse() {
	}

	@Override
	public void print() {
		print("");
	}

	@Override
	public void print(String str) {
		LogUtil.debugAll(this, "{}{}{} ({}kb)", str, "\uD83D\uDCC2", this.name, this.size);
	}

	@Override
	public int getSize() {
		return this.size;
	}
}
