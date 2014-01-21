package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class InclusiveStopFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "testtable");
		
		Scan scan = new Scan();
		Filter filter = new InclusiveStopFilter(Bytes.toBytes("key5"));
		scan.setFilter(filter);
		scan.setStartRow(Bytes.toBytes("key3"));
		ResultScanner scanner = table.getScanner(scan);
		for(Result res : scanner)
			System.out.println(res);
		
		table.close();
	}
}
