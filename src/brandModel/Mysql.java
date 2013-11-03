package brandModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Mysql {

	//数据库连接的包名
	private static String driver = "com.mysql.jdbc.Driver";
	//数据库连接的url
	private static String url;
	//数据库用户名
	private static String user = "root";
	//数据库密码
	private static String password = "123456";
	
	public static Statement statement = null;
	
	public Mysql(String db){
		url = "jdbc:mysql://192.168.128.132:3306/"+db;
	}
	
	public Statement getStatement() throws Exception{
		if(statement == null){
			//System.out.println(url);
			//System.exit(0);
			//加载驱动包
			Class.forName(driver);
			//建立mysql连接
			Connection conn = DriverManager.getConnection(url,user,password);
			//判断连接是否开启
			if(conn.isClosed() == false){
				//这个对象能将sql语句发送到数据库去执行
				statement = conn.createStatement();
			}
		}
		return statement;
	}
}
