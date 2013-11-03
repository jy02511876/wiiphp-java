package school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class student {
	
	//数据库连接的包名
	static String driver = "com.mysql.jdbc.Driver";
	//数据库连接的url
	static String url = "jdbc:mysql://127.0.0.1:3306/school";
	//数据库用户名
	static String user = "root";
	//数据库密码
	static String password = "111111";
	
	
	public static void main(String[] args)
	{
		Map<String,String> m = new HashMap<String,String>(){};
		try {
			//加载驱动包
			Class.forName(driver);
			//建立mysql连接
			Connection conn = DriverManager.getConnection(url,user,password);
			//判断连接是否开启
			if(conn.isClosed() == false){
				System.out.println("Connect mysql successed!");
				//这个对象能将sql语句发送到数据库去执行
				Statement statement = conn.createStatement();
				//sql查询语句
				String sql = "select * from student";
				//发送给数据库去执行,并将结果赋予rs变量中
				ResultSet rs = statement.executeQuery(sql);
				System.out.println("id,name,sex,age");
				//循环输出数据
				while(rs.next()){
					//获取当前行的id
					int id = rs.getInt("id");
					//获取当前行的name
					String name = rs.getString("name");
					//获取当前行的sex
					String sex = rs.getString("sex");
					//获取当前行的age
					int age = rs.getInt("age");
					//打印
					m.put(name, sex);
					System.out.println(id+","+name+","+sex+","+age);
				}
				//关闭数据库连接
				conn.close();
			}
			
			
			
			//kjsl;djflsdjflsdkj
		} catch (ClassNotFoundException e) { //如果driver没有找到的话，报错提示
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) { //如果数据库操作失败的话，报错提示
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
