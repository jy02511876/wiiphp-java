package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class IncrementsTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "counters");
		/*
		//递增
		long cnt1 = table.incrementColumnValue(Bytes.toBytes("20110101"), Bytes.toBytes("daily"), Bytes.toBytes("hits"), 1);
		System.out.println(cnt1);
		//持平
		long cnt2 = table.incrementColumnValue(Bytes.toBytes("20110101"), Bytes.toBytes("daily"), Bytes.toBytes("hits"), 0);
		System.out.println(cnt2);
		//递减
		long cnt3 = table.incrementColumnValue(Bytes.toBytes("20110101"), Bytes.toBytes("daily"), Bytes.toBytes("hits"), -1);
		System.out.println(cnt3);
		*/
		//对不同列同时操作
		Increment increment = new Increment(Bytes.toBytes("20110101"));
		increment.addColumn(Bytes.toBytes("daily"), Bytes.toBytes("hits"), 10);
		increment.addColumn(Bytes.toBytes("weekly"), Bytes.toBytes("hits"), 5);
		increment.addColumn(Bytes.toBytes("monthly"), Bytes.toBytes("hits"), 1);
		
		Result result = table.increment(increment);
		for(KeyValue ky : result.raw())
			System.out.println("KV:"+ky+" Value"+Bytes.toLong(ky.getValue()));
		
		table.close();
	}
}
