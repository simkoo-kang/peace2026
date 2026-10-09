/**
 * LambdaCommandMain.java
 */
package kr.pe.pattern.action.command.lambda;

/**
 * <pre>
 * 자바 8에서 도입된 람다식(Lambda Expression)과 함수형 인터페이스(Functional Interface)를 사용하면,
 *  커맨드 패턴의 가장 큰 단점이었던 "수많은 ConcreteCommand 클래스 생성으로 인한 클래스 폭발" 문제를 완벽하게 해결할 수 있습니다.
 * 
 * 람다식을 사용하면 별도의 클래스 파일(.java)을 만들지 않고도, 명령(행위) 자체를 메서드의 인자로 즉시 전달할 수 있습니다.
 * 
 * 
 * 🛠️ 무엇이 달라졌나요?
 * 1. 클래스 파일의 획기적인 감소
 *  - 기존: LightOnCommand.java, LightOffCommand.java, StereoOnCommand.java 등 명령마다 클래스가 필요했습니다.
 *  - 변경: 명령 클래스가 0개가 되며, 인터페이스와 리모컨, 호출 부만 남습니다.
 * 2. 가독성 및 생산성 향상
 *  - 불필요한 보일러플레이트 코드(생성자 선언, 필드 주입 등)가 사라지고, "어떤 객체의 어떤 메서드를 실행할 것인가"가 직관적으로 보입니다.
 * 
 * PC  2026. 9. 9. 오후 2:49:20
 * </pre>
 */
public class LambdaCommandMain {

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 9. 오후 2:49:20
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		LambdaRemoteControl remote = new LambdaRemoteControl();
		LambdaLight livingRoomLight = new LambdaLight();
		LambdaStereo stereo = new LambdaStereo();

		// [방법 1] 람다식 사용: 구체 클래스 없이 즉석에서 명령 정의
		remote.setCommand(() -> livingRoomLight.on());
		remote.pressButton(); // 출력: 조명이 켜졌습니다.

		remote.setCommand(() -> livingRoomLight.off());
		remote.pressButton(); // 출력: 조명이 꺼졌습니다.

		// [방법 2] 메서드 참조(Method Reference) 사용: 더 간결한 표현
		remote.setCommand(stereo::on);
		remote.pressButton(); // 출력: 오디오가 켜졌습니다.
	}
}
