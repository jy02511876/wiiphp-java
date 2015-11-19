package school;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Counter {
	private Classes[] classes;
	private Teacher[] teacher;
	private Student[] student;
	private Statement stmt;
	
	public Counter(){
		Mysql mysql = Mysql.getInstance();
		Connection conn = mysql.getConn();
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public HashMap<String, Integer> topNames(){
		String sql = "select * from student";
		ResultSet studentRs;
		HashMap<String,Integer> firstNames = new HashMap<String,Integer>();
		try {
			studentRs = stmt.executeQuery(sql);
			while(studentRs.next()){
				String firstName = studentRs.getString("name").substring(0, 1);
				if(firstNames.containsKey(firstName)){
					int count = firstNames.get(firstName);
					count++;
					firstNames.put(firstName, count);
				}else{
					firstNames.put(firstName, 1);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for(Map.Entry<String, Integer> m : firstNames.entrySet()){
			System.out.println(m.getKey()+":"+m.getValue());
		}
		System.out.println(firstNames.size());
		return firstNames;
	}
	
	
	public static void main(String[] args){
		Counter c = new Counter();
		c.topNames();
	}
}
