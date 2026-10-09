/**
 * MementoMain.java
 */
package kr.pe.pattern.action.memento;

import kr.pe.util.LogUtil;

/**
 * <pre>
 * 소프트웨어 디자인 패턴에서 메멘토 패턴(Memento Pattern)은 객체의 구현 세부 사항을 공개하지 않으면서(캡슐화 유지),
 *  객체의 과거 상태를 저장하고 필요할 때 이전 상태로 복구(되돌리기)할 수 있도록 돕는 행위 패턴입니다.
 * 
 * 우리가 흔히 쓰는 단축키 Ctrl + Z (Undo, 되돌리기) 기능이나 게임의 세이브/로드 기능을 구현할 때 핵심이 되는 패턴입니다.
 * 
 * 
 * 💡 왜 메멘토 패턴을 쓸까요? (문제 상황)
 *  - 객체의 상태를 저장하고 복구하려면, 보통 객체 내부의 데이터를 밖으로 꺼내서 어딘가에 저장해 두어야 합니다.
 *    하지만 이 방식을 쓰면 다음과 같은 문제가 생깁니다.
 * 
 * 1. 캡슐화 파괴: 상태를 저장하기 위해 객체 내부의 private 변수들을 public으로 열거나 getter를 남발해야 합니다.
 * 2. 높은 결합도: 외부의 저장소 클래스가 해당 객체의 내부 구조를 너무 잘 알게 되어, 객체의 구조가 조금만 바뀌어도 저장 시스템 전체가 고장 납니다.
 * 
 * 메멘토 패턴은 상태 저장을 전담하는 별도의 '스냅숏(Memento)' 객체를 두어 이 문제를 해결합니다.
 * 
 * 
 * 🏗️ 주요 구성 요소 (3개 역할)
 * 1. Originator (원조자):
 *  - 주인공 객체입니다. 현재 자신의 상태를 담은 메멘토를 생성하거나, 메멘토를 받아 이전 상태로 복구하는 메서드를 가집니다.
 * 2. Memento (메멘토):
 *  - 특정 시점의 Originator 상태를 보관하는 스냅숏 객체입니다.
 *    Originator 외에는 아무도 이 내부 데이터를 수정할 수 없도록 철저히 보호(캡슐화)됩니다.
 * 3. Caretaker (관리자):
 *  - 메멘토를 보관하는 역할을 합니다. 히스토리 리스트(Stack 등)를 관리하며, Undo가 필요할 때 메멘토를 꺼내서 Originator에게 전달합니다.
 *    (단, Caretaker는 메멘토 내부 데이터를 보거나 수정할 수 없습니다.)
 * 
 * 
 * ⚖️ 장점과 단점
 * 1. 장점 ⭕
 *  - 캡슐화 유지: 외부 객체에 내부 상태를 노출하지 않고도 상태 백업이 가능합니다.
 *  - 단일 책임 원칙(SRP): 복구 및 히스토리 관리를 Caretaker가 전담하여 구조가 깔끔해집니다.
 *  - 쉬운 복구: 이전 상태로 되돌리는 로직이 매우 단순해집니다.
 * 2. 단점 ❌
 *  - 메모리 소비: 상태를 자주 저장하거나, 객체 자체가 크면 메멘토가 RAM을 많이 차지합니다.
 *  - 자원 관리 부담: 오래된 메멘토를 주기적으로 삭제(Garbage Collection 등)해주는 관리가 필요합니다.
 * 
 * 
 * 🎯 주로 사용되는 곳
 *  - 텍스트 편집기나 그래픽 툴의 Undo / Redo (되돌리기 / 다시 실행) 기능.
 *  - 바둑, 체스, RPG 게임 등에서 특정 시점으로 플레이를 되돌리는 세이브 시스템.
 *  - 트랜잭션(Transaction) 처리 중 오류가 발생했을 때 이전 상태로 되돌리는 롤백(Rollback) 메커니즘.
 * 
 * PC  2026. 9. 10. 오후 9:33:35
 * </pre>
 */
public class MementoMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 10. 오후 9:33:35
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		TextEditor editor = new TextEditor();
		HistoryCareTaker caretaker = new HistoryCareTaker();

		// 1. 첫 번째 상태 작성 후 저장
		editor.setContent("Version 1.0");
		caretaker.push(editor.save());
		LogUtil.debugAll(MementoMain.class, "현재 내용: {}", editor.getContent());

		// 2. 두 번째 상태 작성 후 저장
		editor.setContent("Version 2.0 (수정됨)");
		caretaker.push(editor.save());
		LogUtil.debugAll(MementoMain.class, "현재 내용: {}", editor.getContent());

		// 3. 세 번째 상태 작성 (저장 안 함)
		editor.setContent("Version 3.0 (실수로 지움)");
		LogUtil.debugAll(MementoMain.class, "현재 내용: {}", editor.getContent());

		// 4. Undo 실행 (Ctrl + Z 효과)
		editor.restore(caretaker.pop()); // 현재 엉망이 된 3.0에서 2.0으로 복구
		LogUtil.debugAll(MementoMain.class, "--- Undo 실행 1 ---");
		LogUtil.debugAll(MementoMain.class, "복구된 내용: {}", editor.getContent());

		editor.restore(caretaker.pop()); // 2.0에서 다시 1.0으로 복구
		LogUtil.debugAll(MementoMain.class, "--- Undo 실행 2 ---");
		LogUtil.debugAll(MementoMain.class, "복구된 내용: {}", editor.getContent());
	}
}
