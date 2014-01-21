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
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class RowFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "testtable");
		
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		
		//比较运算
		System.out.println("filter1 start...");
		Filter filter1 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("row3")));
		scan.setFilter(filter1);
		ResultScanner scanner1 = table.getScanner(scan);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("filter1 end...");
		
		//正则运算
		System.out.println("filter2 start...");
		Filter filter2 = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(".*2"));
		scan.setFilter(filter2);
		ResultScanner scanner2 = table.getScanner(scan);
		for(Result res : scanner2)
			System.out.println(res);
		scanner2.close();
		System.out.println("filter2 end...");
		
		//子串匹配
		System.out.println("filter3 start...");
		Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("row"));
		scan.setFilter(filter3);
		ResultScanner scanner3 = table.getScanner(scan);
		for(Result res : scanner3)
			System.out.println(res);
		scanner3.close();
		System.out.println("filter3 end...");
		
		table.close();
	}
}
