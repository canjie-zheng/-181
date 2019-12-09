package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * 数据库连接
 */
public class Conn {

	// 启动jar包路径
	private static String driver = "com.mysql.jdbc.Driver";

	// 数据库路径
	private static String url = "jdbc:mysql://localhost:3306/course";

	// 用户名
	private static String userName = "root";

	// 密码
	private static String password = "test123456";

	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		System.out.println(new Conn().getConnection());
	}

}
