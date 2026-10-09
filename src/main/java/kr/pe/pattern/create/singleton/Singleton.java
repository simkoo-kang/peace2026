/**
 * Singleton.java
 */
package kr.pe.pattern.create.singleton;

/**
 * <pre>
 * private으로 생성자를 숨기고, getInstance()라는 정적 팩토리 메소드로만 인스턴스를 반환받을 수 있다.
 * 
 * 싱글스레드 환경에선 문제가 되지 않는다.
 * 하지만 멀티스레드 환경에선 다수 스레드가 메소드에 진입하여 경합을 벌이는 과정(Raced Condition)에서 서로 다른 2개의 인스턴스가 생성될 수 있다.
 * 
 * PC  2026. 9. 8. 오전 8:39:14
 * </pre>
 */
public class Singleton {

	private static Singleton instance = null;

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private Singleton() {
		super();
	}
}

/**
 * <pre>
 * private으로 생성자를 숨기고, 클래스 초기화 시 인스턴스를 생성하여 static 멤버 변수에 할당한다.
 * 
 * getInstance()는 싱글톤 인스턴스를 반환하기만 하므로 팩토리 메소드라 칭하긴 어렵다.
 * 
 * 스프링에서 쓰는 방식
 * 스프링에선 Lazy-Intialization을 사용하지 않는 기본적인 경우, 어플리케이션을 구동하면서
 * 
 * ApplicationContext 내에 해당 방법과 같이 인스턴스를 생성하게 된다.
 * 
 * 다만 미사용 인스턴스임에도 생성되어 불필요한 시스템 리소스를 낭비할 수도 있다는 단점이 존재한다.
 * 
 * PC  2026. 9. 8. 오전 8:42:03
 * </pre>
 */
class Singleton2 {

	private static Singleton2 instance = new Singleton2();

	public static Singleton2 getInstance() {
		return instance;
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private Singleton2() {
		super();
	}
}

/**
 * <pre>
 * 메소드 동기화로 Thread-Safe하지만 성능이 매우 떨어진다.
 * 
 * PC  2026. 9. 8. 오전 8:44:39
 * </pre>
 */
class Singleton3 {

	private static Singleton3 instance = null;

	public synchronized static Singleton3 getInstance() {
		if (instance == null) {
			instance = new Singleton3();
		}
		return instance;
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private Singleton3() {
		super();
	}
}

/**
 * <pre>
 * 구현4 - LazyHolder (Thread-Safe, 강력추천)
 * 
 * PC  2026. 9. 8. 오전 8:47:25
 * </pre>
 */
class Singleton4 {

	private static class LazyHolder {
		private static final Singleton4 INSTANCE = new Singleton4();
	}

	public static Singleton4 getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private Singleton4() {
		super();
	}
}

/**
 * <pre>
 * 이 방법은 이러한 자원 낭비를 막고, 싱글톤 객체에 처음 액세스할 때 인스턴스가 생성된다.
 *  또한 synchronized 예약어를 통해 멀티 스레드 환경에서 객체가 두 개 이상 생성되지 않도록 막는다.
 *  
 *  멀티스레드환경에서 이 방식이 제일 좋은 방법이다
 * 
 * PC  2026. 9. 8. 오전 8:52:40
 * </pre>
 */
class Singleton5 {

	private static Singleton5 INSTANCE = null;

	public static Singleton5 getInstance() {
		if (INSTANCE == null) {
			synchronized (Singleton5.class) {
				if (INSTANCE == null) {
					INSTANCE = new Singleton5();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * <pre></pre>
	 * 
	 */
	private Singleton5() {
		super();
	}
}
