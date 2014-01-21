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
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;



public class FilterListTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "testtable");
		
		//过滤器集合
		List<Filter> filters = new ArrayList<Filter>();
		
		Filter filter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,new BinaryComparator(Bytes.toBytes("key5")));
		filters.add(filter1);
		Filter filter2 = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,new BinaryComparator(Bytes.toBytes("key10")));
		filters.add(filter2);
		Filter filter3 = new QualifierFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("key.*"));
		filters.add(filter3);
		
		System.out.println("filterList1 start....");
		FilterList filterList1 = new FilterList(filters);
		Scan scan1 = new Scan();
		scan1.setFilter(filterList1);
		ResultScanner scanner1 = table.getScanner(scan1);
		for(Result res : scanner1)
			System.out.println(res);
		scanner1.close();
		System.out.println("filterList1 end....");
		
		System.out.println("filterList2 start....");
		FilterList filterList2 = new FilterList(FilterList.Operator.MUST_PASS_ONE,filters);
		Scan scan2 = new Scan();
		scan1.setFilter(filterList2);
		ResultScanner scanner2 = table.getScanner(scan2);
		for(Result res : scanner2)
			System.out.println(res);
		scanner2.close();
		System.out.println("filterList2 end....");
		
		table.close();
	}
}
