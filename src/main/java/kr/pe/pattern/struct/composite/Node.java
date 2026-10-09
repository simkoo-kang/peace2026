/**
 * Node.java
 */
package kr.pe.pattern.struct.composite;

import java.util.List;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:19:43
 * </pre>
 */
public interface Node extends Comparable<Node> {

	// 계층 트리 출력
	public void print();

	public void print(String str);

	// 파일/폴더 용량 얻기
	public int getSize();

	public int getNodeCount();

	public int getFileCount();

	public int getDirectoryCount();

	public String getName();

	public List<Node> getFiles();

	public boolean isFile();

	public boolean isDirectory();

	public boolean add(Node node);

	public void sort();

	public void sortReverse();

}
