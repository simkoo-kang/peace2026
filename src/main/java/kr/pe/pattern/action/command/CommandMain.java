/**
 * CommandMain.java
 */
package kr.pe.pattern.action.command;

/**
 * <pre>
 * **커맨드 패턴(Command Pattern)**은 요청을 하나의 독립적인 객체로 캡슐화하여,
 *  서로 다른 요청을 수행하는 클라이언트를 매개변수화할 수 있도록 만드는 행위(Behavioral) 디자인 패턴입니다.
 * 
 * 쉽게 말해, "수행할 작업(명령)" 자체를 하나의 자바 객체로 만들어서 언제든 전달하고, 저장하고, 재사용할 수 있게 하는 패턴입니다.
 * 
 * 
 * 💡 핵심 개념 및 구성 요소
 * 
 * 커맨드 패턴은 작업을 요청하는 객체와 그 작업을 실제로 수행하는 객체를 **완전히 분리(Decoupling)**시킵니다.
 * 1. Command (인터페이스):
 *    모든 명령 객체가 구현해야 하는 인터페이스입니다.
 *    보통 execute() 메서드 하나만 가집니다.
 * 2. ConcreteCommand (구체적인 명령):
 *    Command 인터페이스를 구현하며, 실제 일을 하는 객체(Receiver)와 연결되어 execute()가 호출될 때 Receiver의 메서드를 실행합니다.
 * 3. Receiver (수신자):
 *    실제로 비즈니스 로직(기능)을 수행하는 대상입니다. (예: 불 켜기, 에어컨 켜기 등)
 * 4. Invoker (발동자):
 *    명령을 보관하고 있다가 필요할 때 Command의 execute()를 호출하는 역할입니다. (예: 만능 리모컨의 버튼)
 * 
 * ⚖️ 장단점
 * 
 * 1. 장점
 *  - 결합도 감소 (Decoupling):
 *    작업을 요청하는 객체(Invoker)와 실제 수행하는 객체(Receiver)가 완전히 분리되어 코드가 유연해집니다.
 *  - 확장성:
 *    기존 코드를 수정하지 않고 새로운 명령어(ConcreteCommand)를 얼마든지 추가할 수 있습니다. (개방-폐쇄 원칙 OCP 만족)
 *  - 기능적 유연성:
 *    명령을 큐에 저장해 두었다가 나중에 실행(지연 실행)하거나, 로그를 남기거나, Undo(되돌리기) / Redo(다시 실행) 기능을 쉽게 구현할 수 있습니다.
 * 
 * 2. 단점
 *  - 각각의 명령마다 새로운 클래스를 정의해야 하므로, 명령의 종류가 많아질수록 클래스 개수가 대폭 늘어나 구조가 복잡해질 수 있습니다.
 * 
 * PC  2026. 9. 9. 오후 2:31:56
 * </pre>
 */
public class CommandMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 2:31:56
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		RemoteControl remote = new RemoteControl(); // Invoker
		Light livingRoomLight = new Light(); // Receiver
		Stereo stereo = new Stereo(); // Receiver

		// 1. 조명 켜기 명령 장착 및 실행
		Command lightOn = new LightOnCommand(livingRoomLight);
		remote.setCommand(lightOn);
		remote.pressButton();

		// 2. 오디오 켜기 명령 장착 및 실행 (리모컨 코드는 그대로 재사용)
		Command stereoOn = new StereoOnCommand(stereo);
		remote.setCommand(stereoOn);
		remote.pressButton();
	}

}
