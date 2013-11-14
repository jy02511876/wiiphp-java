package hadoop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClient {
	
	private static String driver = "org.apache.hadoop.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive://192.168.128.132:10000/s_cn";
	
	
	public static void main(String[] args) throws SQLException{
		try {
			Class.forName(driver);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(url,"","");
		Statement statement = conn.createStatement();
		String sql = "select count(*) from s_cn.list";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getInt(1));
		}
		
		sql = "select count(*) from s_cn.list where pt=\"2013-11-12\"";
		rs = statement.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getInt(1));
		}
		
	}
}
