/**
 * IteratorTest.java
 */
package kr.pe.pattern.action.iterator;

/**
 * <pre>
 * 패턴 사용 시기
 * 
 * 컬렉션에 상관없이 객체 접근 순회 방식을 통일하고자 할 때
 * 컬렉션을 순회하는 다양한 방법을 지원하고 싶을 때
 * 컬렉션의 복잡한 내부 구조를 클라이언트로 부터 숨기고 싶은 경우 (편의 + 보안)
 * 데이터 저장 컬렉션 종류가 변경 가능성이 있을 때
 * 
 * 클라이언트가 집합 객체 내부 표현 방식을 알고 있다면, 표현 방식이 달라지면 클라이언트 코드도 변경되어야 하는 문제가 생긴다.
 * 
 * 패턴 장점
 * 
 * 일관된 이터레이터 인터페이스를 사용해 여러 형태의 컬렉션에 대해 동일한 순회 방법을 제공한다.
 * 컬렉션의 내부 구조 및 순회 방식을 알지 않아도 된다.
 * 집합체의 구현과 접근하는 처리 부분을 반복자 객체로 분리해 결합도Visit Website를 줄 일 수 있다.
 * 
 * Client에서 iterator로 접근하기 때문에 ConcreteAggregate 내에 수정 사항이 생겨도 iterator에 문제가 없다면 문제가 발생하지 않는다.
 * 
 * 순회 알고리즘을 별도의 반복자 객체에 추출하여 각 클래스의 책임을 분리하여 단일 책임 원칙(SRP)Visit Website를 준수한다.
 * 데이터 저장 컬렉션 종류가 변경되어도 클라이언트 구현 코드는 손상되지 않아 수정에는 닫혀 있어 개방 폐쇄 원칙(OCP)Visit Website를 준수한다.
 * 
 * 패턴 단점
 * 
 * 클래스가 늘어나고 복잡도가 증가한다.
 * 
 * 만일 앱이 간단한 컬렉션에서만 작동하는 경우 패턴을 적용하는 것은 복잡도만 증가할 수 있다.
 * 이터레이터 객체를 만드는 것이 유용한 상황인지 판단할 필요가 있다.
 * 
 * 구현 방법에 따라 캡슐화를 위배할 수 있다.
 * 
 * PC  2026. 9. 8. 오후 6:59:27
 * </pre>
 */
public class IteratorTest {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오후 6:59:27
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. 집합체 생성
		ConcreteAggregate aggregate = new ConcreteAggregate(5);
		aggregate.add(1);
		aggregate.add(2);
		aggregate.add(3);
		aggregate.add(4);
		aggregate.add(5);

		// 2. 집합체에서 이터레이터 객체 반환
		Iterator iter = aggregate.iterator();

		// 3. 이터레이터 내부 커서를 통해 순회
		while (iter.hasNext()) {
			System.out.printf("%s → ", iter.next());
		}
	}

}
