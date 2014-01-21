package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class ScanTest {
	private static Configuration conf;
	private static HTable table;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		table = new HTable(conf, "testtable");
		System.out.println("scanner1 start....");
		Scan scan1 = new Scan();
		ResultScanner scanner1 = table.getScanner(scan1);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("scanner1 end....");
		
		System.out.println("scanner2 start....");
		Scan scan2 = new Scan();
		scan2.addFamily(Bytes.toBytes("colfam1"));
		ResultScanner scanner2 = table.getScanner(scan2);
		for(Result res : scanner2)
			System.out.println(res);
		scanner2.close();
		System.out.println("scanner2 end....");
		
		System.out.println("scanner3 start....");
		Scan scan3 = new Scan();
		scan3.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"))
			.setStartRow(Bytes.toBytes("row1"))
			.setStopRow(Bytes.toBytes("row4"));
		ResultScanner scanner3 = table.getScanner(scan3);
		for(Result res : scanner3)
			System.out.println(res);
		scanner3.close();
		System.out.println("scanner3 end....");
	}
}
