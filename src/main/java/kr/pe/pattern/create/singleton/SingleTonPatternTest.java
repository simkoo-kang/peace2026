/**
 * SingleTonPatternTest.java
 */
package kr.pe.pattern.create.singleton;

/**
 * <pre>
 * 클래스의 인스턴스, 즉 객체를 하나만 만들어 사용하는 패턴
 * 인스턴스를 하나만 만들어 사용하기위한 패턴 (커넥션 풀, 스레드 풀, 디바이스 설정 등)
 * 
 * 주로 서버와 Socket Connection, DB JDBC Connetion, 스프링의 Bean 등에 많이 활용!
 * 
 * < 싱슬턴 패턴 만들기 과정 >
 * new를 실행할 수 옶도록 생성자에 private 접근 제어자를 지정한다.
 * 유일한 단일 객체를 반환할 수 있는 정적 메서드가 필요하다.
 * 유일한 단일 객체를 참조할 정적 참조 변수가 필요하다.
 * 
 * PC  2026. 9. 8. 오전 8:16:19
 * </pre>
 */
public class SingleTonPatternTest {

	private static class SingleTon {
		static SingleTon singletonObject; // 정적 참조 변수

		private SingleTon() {
		} // private 생성자

		// 객체 반환 정적 메서드
		public static SingleTon getInstance() {
			if (singletonObject == null) {
				singletonObject = new SingleTon();
			}
			return singletonObject;
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 8. 오전 8:16:19
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// private 생성자이므로 new를 통해 인스턴스를 생성할 수 없음
		// Singleton s = new Singleton();

		SingleTon s1 = SingleTon.getInstance();
		SingleTon s2 = SingleTon.getInstance();
		SingleTon s3 = SingleTon.getInstance();

		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		/**
		 * com.grace.oop.singleton.Singleton@26f0a63f
		 * com.grace.oop.singleton.Singleton@26f0a63f
		 * com.grace.oop.singleton.Singleton@26f0a63f
		 */
	}

}
