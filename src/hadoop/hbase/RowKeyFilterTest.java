package hadoop.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

public class RowKeyFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "mobile_service");
		
		Scan scan = new Scan();
		scan.setCaching(100);  
		
		System.out.println("scan start...");
		
		String uid = "2594019581396786759";
		//String datetime = "1397295373";
		//Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator(uid));
		List<Filter> filters = new ArrayList<Filter>();
		Filter uidFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,new BinaryPrefixComparator(Bytes.toBytes(uid)));
		
		Filter pageFilter = new PageFilter(10);
		
		filters.add(uidFilter);
		filters.add(pageFilter);
		
		FilterList filterList1 = new FilterList(filters);
		
		scan.setFilter(filterList1);
		//scan.addColumn(Bytes.toBytes("api"), Bytes.toBytes(""));
		ResultScanner scanner3 = table.getScanner(scan);
		try{
			for(Result res : scanner3){
				System.out.println(res);
				for (KeyValue kv : res.raw()) {
					
	                System.out.println("row:" + Bytes.toString(kv.getRow()));
	                System.out
	                        .println("value:" + Bytes.toString(kv.getValue()));
	                System.out
	                        .println("-------------------------------------------");
	            }
				
			}
		} finally{
			scanner3.close();
		}
		
		System.out.println("end...");
		
		table.close();
	}
}
