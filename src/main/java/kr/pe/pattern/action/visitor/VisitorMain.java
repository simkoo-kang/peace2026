/**
 * VisitorMain.java
 */
package kr.pe.pattern.action.visitor;

import java.util.List;

import kr.pe.util.LogUtil;
import kr.pe.util.Str;

/**
 * <pre>
 * 방문자 패턴(Visitor Pattern)은 데이터 구조(클래스 구조)와 그 구조에 적용할 행동(알고리즘)을 완전히 분리하는 행위(Behavioral) 디자인 패턴입니다.
 * 
 * 이 패턴을 사용하면 기존 데이터 구조 클래스들을 전혀 수정하지 않고도 새로운 연산(비즈니스 로직)을 언제든 추가할 수 있어,
 *  객체지향 설계 원칙 중 하나인 개방-폐쇄 원칙(OCP)을 강력하게 극대화합니다.
 * 
 * 
 * 💡 핵심 메커니즘:
 * 더블 디스패치 (Double Dispatch)방문자 패턴의 핵심은 "더블 디스패치"라는 독특한 방식으로 작동한다는 점입니다.
 * 
 *  - 데이터 구조가 방문자(Visitor)를 받아들이고 (element.accept(visitor))
 *  - 방문자는 자신을 보낸 데이터 구조의 구체적인 타입을 확인하여 알맞은 로직을 수행합니다 (visitor.visit(element))
 * 
 * 
 * 쇼핑몰에서 컴퓨터 부품(데이터 구조)을 구매할 때, 일반 고객용 가격과 블랙프라이데이 할인 가격(방문자/로직)을 다르게 적용하는 예시입니다.
 * 
 * 1. Visitor(방문자) 인터페이스와 Element(요소) 인터페이스
 * 2. ConcreteElement (구체적인 데이터 구조 부품들)
 *  - 부품 클래스들은 내부 데이터를 가지고 있으며, 방문자가 오면 문을 열어주듯(accept) 전달할 뿐 스스로 계산 로직을 가지지 않습니다.
 * 3. ConcreteVisitor (구체적인 비즈니스 로직들)
 *  - 이제 부품 클래스를 수정하지 않고, 방문자만 바꿔서 새로운 계산 로직들을 추가할 수 있습니다.
 *  로직 1: 일반 가격 계산기
 *  로직 2: 블랙프라이데이 20% 할인 가격 계산기 (기존 CPU, GPU 코드 변경 없음)
 * 
 * 
 * ⚖️ 장단점
 * 
 * 1. 장점
 *  - 개방-폐쇄 원칙(OCP) 만족:
 *    데이터 구조 클래스를 전혀 수정하지 않고도, 새로운 동작(Visitor)을 무한히 확장할 수 있습니다.
 *  - 단일 책임 원칙(SRP) 만족:
 *    복잡한 연산이나 비즈니스 로직들이 데이터 객체 내부에서 분리되어 별도의 Visitor 클래스로 집중되므로 깔끔해집니다.
 * 
 * 2. 단점
 *  - 새로운 데이터 요소 추가의 취약성:
 *    만약 Ram 이라는 새로운 부품 클래스가 추가되면,
 *     모든 Visitor 인터페이스와 자식 클래스들을 전부 수정(visit(Ram ram))해야 하는 치명적인 단점이 있습니다.
 *  - 객체의 정보를 Visitor가 들여다봐야 하므로, 캡슐화가 다소 약화될 수 있습니다.
 * 
 * PC  2026. 9. 9. 오후 6:07:07
 * </pre>
 */
public class VisitorMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 6:07:07
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 복합 데이터 구조 객체 생성
		List<ComputerPart> computer = List.of(new Cpu(), new Gpu());

		// 1. 일반 가격 방문자 투입
		PriceVisitor regularCalculator = new PriceVisitor();
		for (ComputerPart part : computer) {
			part.accept(regularCalculator);
		}
		LogUtil.debugAll(VisitorMain.class, "일반 총 가격: {} 원", Str.numberFormat(regularCalculator.getTotal()));

		// 2. 블프 할인 가격 방문자 투입 (기존 데이터 구조는 그대로 재사용)
		BlackFridayVisitor bfCalculator = new BlackFridayVisitor();
		for (ComputerPart part : computer) {
			part.accept(bfCalculator);
		}
		LogUtil.debugAll(VisitorMain.class, "블프 할인 가격: {} 원", Str.numberFormat(bfCalculator.getTotal()));
	}

}
