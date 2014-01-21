package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class PrefixFilterTest {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "testtable");
		
		Scan scan = new Scan();
		Filter filter = new PrefixFilter(Bytes.toBytes("row"));
		scan.setFilter(filter);
		ResultScanner scanner = table.getScanner(scan);
		for(Result res : scanner)
			System.out.println(res);
		scanner.close();
		
		table.close();
	}
}
