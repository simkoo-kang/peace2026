/**
 * MySqlHelper.java
 */
package kr.pe.pattern.struct.facade;

import java.sql.Connection;

import kr.pe.util.Sys;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 9:59:46
 * </pre>
 */
public class MySqlHelper {

	public static Connection getMySqlDBConnection() {
		// 실제 커넥션을 리턴해야 하지만, 예제이기에 null 을 리턴하겠습니다.
		return null;
	}

	public void generateMySqlPDFReport(String tableName, Connection con) {
		System.out.println(Sys.getClassNameOfCaller() + " " + Sys.getClassAndMethodNameOfCaller() + " " + tableName
				+ " PDF Report");
	}

	public void generateMySqlHTMLReport(String tableName, Connection con) {
		System.out.println(Sys.getClassNameOfCaller() + " " + Sys.getClassAndMethodNameOfCaller() + " " + tableName
				+ " HTML Report");
	}
}
