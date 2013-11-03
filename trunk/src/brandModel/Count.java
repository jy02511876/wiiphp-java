package brandModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Count {
	public static void main(String[] args) throws SQLException,Exception{
		String sql = "select count(*) from brand_model";
		Statement statement;
		Mysql mysql = new Mysql("dict");
		statement = mysql.getStatement();
		ResultSet rs = statement.executeQuery(sql);
		rs.first();
		System.out.println(rs.getInt(1));
	}
}
