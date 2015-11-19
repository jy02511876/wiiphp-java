package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Curd {
	private static Configuration conf;
	private static HTable table;
	private static String tableName = "test";
	
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		System.out.println("start create table '"+tableName+"' ...... ");
		HBaseAdmin hbaseAdmin = new HBaseAdmin(conf);
		if(hbaseAdmin.tableExists(tableName)){
			hbaseAdmin.disableTable(tableName);
			hbaseAdmin.deleteTable(tableName);
			System.out.println(tableName+"exist,so deleted ......");
		}
		
		HTableDescriptor descriptor = new HTableDescriptor(tableName);
		descriptor.addFamily(new HColumnDescriptor("info"));
		descriptor.addFamily(new HColumnDescriptor("remark"));
		hbaseAdmin.createTable(descriptor);
		System.out.println("end create table ......");
		
		System.out.println("start insert data ...... ");
		HTable table = new HTable(conf, tableName);
		List<Put> puts = new ArrayList<Put>();
		
		Put put1 = new Put(Bytes.toBytes("row1"));
		put1.add(Bytes.toBytes("info"),Bytes.toBytes("name"),Bytes.toBytes("zhou"));
		puts.add(put1);
		
		Put put2 = new Put(Bytes.toBytes("row1"));
		put2.add(Bytes.toBytes("info"),Bytes.toBytes("sex"),Bytes.toBytes("male"));
		puts.add(put2);
		
		Put put3 = new Put(Bytes.toBytes("row1"));
		put3.add(Bytes.toBytes("remark"),Bytes.toBytes("age"),Bytes.toBytes("29"));
		puts.add(put3);
		
		table.put(puts);
		System.out.println("end insert data ...... ");

		System.out.println("start scan data ...... ");
		Scan scan1 = new Scan();
		ResultScanner scanner1 = table.getScanner(scan1);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("end scan data....");
	}
}
