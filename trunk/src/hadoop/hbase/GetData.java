package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class GetData {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		//取单条记录
		HTable table = new HTable(conf,"testtable");
		Get get = new Get(Bytes.toBytes("row1"));
		get.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		Result result = table.get(get);
		byte[] val = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		System.out.println("value:"+Bytes.toString(val));
		
		//批量取数据
		byte[] row1 = Bytes.toBytes("row1");
		byte[] row2 = Bytes.toBytes("row2");
		byte[] row3 = Bytes.toBytes("row3");
		
		byte[] family = Bytes.toBytes("colfam1");
		byte[] qual = Bytes.toBytes("qual1");
		
		List<Get> gets = new ArrayList<Get>();
		
		Get get1 = new Get(row1);
		get1.addColumn(family, qual);
		gets.add(get1);
		
		Get get2 = new Get(row2);
		get2.addColumn(family,qual);
		gets.add(get2);
		
		Get get3 = new Get(row3);
		get2.addColumn(family,qual);
		gets.add(get3);
		
		Result[] results = table.get(gets);
		for(Result rs : results){
			if(rs.containsColumn(family, qual)){
				byte[] row = rs.getRow();
				byte[] value = rs.getValue(family, qual);
				
				System.out.println("key:"+Bytes.toString(row)+",value:"+Bytes.toString(value));
			}
		}
		
		table.close();
	}
}
