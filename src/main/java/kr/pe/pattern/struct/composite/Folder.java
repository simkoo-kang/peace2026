/**
 * Folder.java
 */
package kr.pe.pattern.struct.composite;

import java.util.ArrayList;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 
 * PC  2026. 9. 9. 오전 8:20:50
 * </pre>
 */
public class Folder extends FileNode {

	public Folder(String name) {
		super(name);
		this.files = new ArrayList<>();
	}

	// 공백 indent 표현 처리를 위한 print 메서드 오버로딩
	@Override
	public void print() {
		this.print("");
	}

	@Override
	public void print(String str) {
		int size = getSize(); // 폴더가 담고 있는 모든 파일에 대한 용량 합산

		LogUtil.debugAll(this, "{}{}{} ({}kb)", str, "\uD83D\uDCC2", this.name, size);

		for (Node node : this.files) {
			node.print(str + "    ");
		}
	}

	// 각 파일의 용량(kb) 구하기
	@Override
	public int getSize() {
		int sum = 0;
		for (Node node : this.files) {
			sum += node.getSize(); // print 로직과 똑같이 재귀 동작
		}
		return sum;
	}
}
