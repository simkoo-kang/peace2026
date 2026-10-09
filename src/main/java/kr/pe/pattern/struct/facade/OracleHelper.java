/**
 * OracleHelper.java
 */
package kr.pe.pattern.struct.facade;

import java.sql.Connection;

import kr.pe.util.Sys;

/**
 * <pre>
 * 
 * PC  2026. 9. 8. 오전 10:06:07
 * </pre>
 */
public class OracleHelper {

	public static Connection getOracleDBConnection() {
		// 실제 커넥션을 리턴해야 하지만, 예제이기에 null 을 리턴하겠습니다.
		return null;
	}

	public void generateOraclePDFReport(String tableName, Connection con) {
		System.out.println(Sys.getClassNameOfCaller() + " " + Sys.getClassAndMethodNameOfCaller() + " " + tableName
				+ " PDF Report");
	}

	public void generateOracleHTMLReport(String tableName, Connection con) {
		System.out.println(Sys.getClassNameOfCaller() + " " + Sys.getClassAndMethodNameOfCaller() + " " + tableName
				+ " HTML Report");
	}
}
