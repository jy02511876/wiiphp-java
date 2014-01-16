package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class BatchCommit {
	private static Configuration conf;
	private static HBaseAdmin admin;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		admin = new HBaseAdmin(conf);
		
		System.out.println("禁用自动Flush，减少RPC(网络交互)，实现快速操作");
		String tableName = "testtable";
		//createTable("test");
		HTable table = new HTable(conf, tableName);
		System.out.println(table.isAutoFlush());
		
		//禁用客户端的自动Flush,采用手动一次性提价
		table.setAutoFlush(false);
		
		Put put1 = new Put(Bytes.toBytes("row1"));
		put1.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("king"));
		table.put(put1);
		
		Put put2 = new Put(Bytes.toBytes("row2"));
		put2.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("maggie"));
		table.put(put2);
		
		Put put3 = new Put(Bytes.toBytes("row3"));
		put3.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("dc"));
		table.put(put3);
		
		Get get = new Get(Bytes.toBytes("row1"));
		Result res1 = table.get(get);
		System.out.println("Results:"+res1);
		
		table.flushCommits();
		Result res2 = table.get(get);
		System.out.println("Results:"+res2);
	}
}
