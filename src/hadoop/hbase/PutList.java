package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class PutList {
	
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		String tableName = "testtable";
		HTable table = new HTable(conf, tableName);
		
		List<Put> puts = new ArrayList<Put>();
		
		/*
		Put put1 = new Put(Bytes.toBytes("row4"));
		put1.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("red"));
		puts.add(put1);
		
		Put put2 = new Put(Bytes.toBytes("row5"));
		put2.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("green"));
		puts.add(put2);
		
		Put put3 = new Put(Bytes.toBytes("row6"));
		put3.add(Bytes.toBytes("colfam1"),Bytes.toBytes("qual1"),Bytes.toBytes("blue"));
		puts.add(put3);
		*/
		for(int i=0;i<100;i++){
			Put put = new Put(Bytes.toBytes("key"+i));
			put.add(Bytes.toBytes("colfam1"),Bytes.toBytes("q1"),Bytes.toBytes(i));
			puts.add(put);
		}
		table.put(puts);
		System.out.println("finished");
		table.close();
	}
}
