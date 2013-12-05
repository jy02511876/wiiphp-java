package school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/school";
	private String user = "root";
	private String pwd = "123456";
	
	public static Connection conn;
	
	private static Mysql instance;
	
	
	private Mysql(){
		
	}
	
	public Connection getConn(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static Mysql getInstance(){
		if(instance == null){
			instance = new Mysql();
		}
		return instance;
	}

}
