package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

public class FamilyFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "testtable");
		
		Scan scan = new Scan();
		System.out.println("filter1 start...");
		Filter filter1 = new FamilyFilter(CompareFilter.CompareOp.LESS,new BinaryComparator(Bytes.toBytes("colfam2")));
		scan.setFilter(filter1);
		ResultScanner scanner1 = table.getScanner(scan);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("filter1 end...");
		
		table.close();
	}
}
