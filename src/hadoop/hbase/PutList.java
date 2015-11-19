package hadoop.hbase;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class PutList {
	
	private static Configuration conf;
	
	public static void main(String[] args) throws Exception{
		System.out.println("start");
		conf = HBaseConfiguration.create();
		
		String tableName = "test";
		HTable table = new HTable(conf, tableName);
		
		List<Put> puts = new ArrayList<Put>();
		
		Put put1 = new Put(Bytes.toBytes("row4"));
		put1.add(Bytes.toBytes("c"),Bytes.toBytes("q1"),Bytes.toBytes("red"));
		puts.add(put1);
		
		Put put2 = new Put(Bytes.toBytes("row5"));
		put2.add(Bytes.toBytes("c"),Bytes.toBytes("q1"),Bytes.toBytes("green"));
		puts.add(put2);
		
		Put put3 = new Put(Bytes.toBytes("row6"));
		put3.add(Bytes.toBytes("c"),Bytes.toBytes("q1"),Bytes.toBytes("blue"));
		puts.add(put3);
		
		/*
		for(int i=0;i<100;i++){
			Put put = new Put(Bytes.toBytes("key"+i));
			put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("q1"),Bytes.toBytes(i));
			puts.add(put);
		}
		
		
		Put spRowKey = new Put(mkRowKey("zhoubin"));
		spRowKey.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("zhoubin"));
		puts.add(spRowKey);
		*/
		table.put(puts);
		
		/*
		Get get = new Get(mkRowKey("zhoubin"));
		get.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		Result result = table.get(get);
		byte[] val = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		System.out.println("value:"+Bytes.toString(val));
		*/
		
		System.out.println("finished");
		table.close();
	}
	
	
	private static byte[] mkRowKey(String user) {
		byte[] userHash = md5sum(user);
		byte[] timestamp = Bytes.toBytes(-1 * 1398516250);
		byte[] rowKey = new byte[24];
		int offset = 0;
	    offset = Bytes.putBytes(rowKey, offset, userHash, 0, userHash.length);
	    Bytes.putBytes(rowKey, offset, timestamp, 0, timestamp.length);
	    return rowKey;
	}
	
	public static byte[] md5sum(String s){
		MessageDigest d;
	    try {
	      d = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	      throw new RuntimeException("MD5 algorithm not available!", e);
	    }

	    return d.digest(Bytes.toBytes(s));
	}
}
