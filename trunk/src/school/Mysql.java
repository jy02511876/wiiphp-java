package school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/school";
	private static String user = "root";
	private static String pwd = "123456";
	private static Statement statement;
	
	public static Statement getSatement(){
		if(statement == null){
			try {
				Class.forName(driver);
				Connection conn = DriverManager.getConnection(url,user,pwd);
				statement = conn.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return statement;
	}
}
