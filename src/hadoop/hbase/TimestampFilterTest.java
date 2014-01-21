package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.TimestampsFilter;

public class TimestampFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "testtable");
		
		List<Long> ts = new ArrayList<Long>();
		ts.add(new Long(1));
		ts.add(new Long(2));
		ts.add(new Long(3));
		Filter filter = new TimestampsFilter(ts);
		
		System.out.println("filter1 start...");
		Scan scan1 = new Scan();
		scan1.setFilter(filter);
		ResultScanner scanner1 = table.getScanner(scan1);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("filter1 end...");
		
		System.out.println("filter2 start...");
		Scan scan2 = new Scan();
		scan2.setFilter(filter);
		scan2.setTimeRange(8, 12);
		ResultScanner scanner2 = table.getScanner(scan2);
		for(Result res : scanner2)
			System.out.println(res);
		scanner2.close();
		System.out.println("filter2 end...");
		
		table.close();
	}
}
