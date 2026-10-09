/**
 * ProxyPatternTest.java
 */
package kr.pe.pattern.struct.proxy;

/**
 * <pre>
 * 제어 흐름을 조정하기 위한 목적으로 중간에 대리자(proxy)를 두는 패턴
 * 
 * 프록시 패턴은 개방 폐쇄 원칙(OCP)과 의존 역전 원칙(DIP)이 적용된 설계 패턴이다.
 * 
 * 객체에 접근을 제어하고 싶을 때, 다른 객체가 위임해서 대리로 해결하는 방식
 * 1) 객체를 실제 사용하기 전까지 초기화를 미루고 싶을 때 (Lazy init)
 * 2) 접근 권한 통제
 * 
 * 스프링에서는 AOP에서는 이 프록시 패턴을 사용한다.
 * 객체를 실제로 부르는 게 아닌, 프록시 객체를 부르고 나중에 프록시 객체가 실제 객체를 호출하는 것이다.
 * 
 * PC  2026. 9. 7. 오후 10:20:44
 * </pre>
 */
public class ProxyPatternTest {

	private static interface CommandExecutor {
		public void runCommand(String cmd) throws Exception;
	}

	private static class CommandExecutorImpl implements CommandExecutor {

		@Override
		public void runCommand(String cmd) throws Exception {
			Runtime.getRuntime().exec(new String[] { cmd });
		}

	}

	private static class CommandExecutorProxy implements CommandExecutor {
		private boolean isAdmin;
		private CommandExecutor executor;

		public CommandExecutorProxy(String user, String pwd) {
			if ("ReadyKim".equals(user) && "correct_pwd".equals(pwd)) {
				this.isAdmin = true;
			}
			this.executor = new CommandExecutorImpl();
		}

		@Override
		public void runCommand(String cmd) throws Exception {
			if (this.isAdmin) {
				this.executor.runCommand(cmd);
			} else {
				if (cmd.trim().startsWith("rm")) {
					throw new Exception("rm command is not allowed for non-admin users.");
				}
				this.executor.runCommand(cmd);
			}
		}
	}

	/**
	 * <pre>
	 * 
	 * PC 2026. 9. 7. 오후 10:20:44
	 * </pre>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CommandExecutor executor = new CommandExecutorProxy("ReadyKim", "wrong_pwd");
		try {
			executor.runCommand("ls -ltr");
			executor.runCommand("rm -rf abc.pdf");
		} catch (Exception e) {
			System.out.println("Exception Message::" + e.getMessage());
		}
	}

}
