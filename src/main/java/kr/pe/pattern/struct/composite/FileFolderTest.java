/**
 * FileFolderTest.java
 */
package kr.pe.pattern.struct.composite;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:29:49
 * </pre>
 */
public class FileFolderTest {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오전 8:29:49
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Folder root = new Folder("root");

		File file1 = new File("file1", 10);
		Folder sub1 = new Folder("sub1");
		Folder sub2 = new Folder("sub2");
//

		root.add(file1);
		root.add(sub2);
		root.add(sub1);

		File file11 = new File("file11", 10);
		File file12 = new File("file12", 10);

		sub1.add(file11);
		sub1.add(file12);

		File file21 = new File("file21", 10);

		sub2.add(file21);

		root.sortReverse();
		// 전체 dir 출력
		root.print();
	}
}
