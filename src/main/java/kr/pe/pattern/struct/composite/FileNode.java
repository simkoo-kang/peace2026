/**
 * FileNode.java
 */
package kr.pe.pattern.struct.composite;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:37:11
 * </pre>
 */
public abstract class FileNode implements Node {

	protected String name;
	protected List<Node> files = null;
	protected boolean isFile;

	/**
	 * <pre></pre>
	 * 
	 * @param name
	 */
	public FileNode(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Node> getFiles() {
		return this.files;
	}

	/**
	 * @return the isFile
	 */
	public boolean isFile() {
		return this.isFile;
	}

	@Override
	public boolean isDirectory() {
		return !this.isFile;
	}

	@Override
	public boolean add(Node node) {
		return this.files.add(node);
	}

	@Override
	public int getNodeCount() {
		return this.files.size();
	}

	@Override
	public int getFileCount() {
		int cnt = 0;
		for (Node node : this.files) {
			if (node.isFile()) {
				cnt++;
			}
		}
		return cnt;
	}

	@Override
	public int getDirectoryCount() {
		int cnt = 0;
		for (Node node : this.files) {
			if (node.isDirectory()) {
				cnt++;
			}
		}
		return cnt;
	}

	// 디렉토리 우선
	@Override
	public void sort() {
		Collections.sort(this.files, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.compareTo(o2);
			}
		});
		if (this.isDirectory()) {
			for (Node node : this.files) {
				node.sort();
			}
		}
	}

	// 파일 우선
	@Override
	public void sortReverse() {
		Collections.sort(this.files, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o2.compareTo(o1);
			}
		});
		if (this.isDirectory()) {
			for (Node node : this.files) {
				node.sortReverse();
			}
		}
	}

	@Override
	public int compareTo(Node o) {
		int cmp = 0;
		if (this.isDirectory() && o.isFile()) {
			cmp = -1;
		} else if (this.isFile() && o.isDirectory()) {
			cmp = 1;
		} else {
			cmp = this.getName().compareTo(o.getName());
		}
		return cmp;
	}
}
