package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class PageFilterTest {
	private static final byte[] POSTFIX = new byte[] { 0x00 };
	
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "testtable");
		
		Filter filter = new PageFilter(3);
		
		int totalRows = 0;
		byte[] lastRow = null;
		int page = 1;
		while(true){
			System.out.println("page:"+page);
			page++;
			
			Scan scan = new Scan();
			scan.setFilter(filter);
			if(lastRow != null){
				byte[] startRow = Bytes.add(lastRow, POSTFIX);
				System.out.println("startRow:"+Bytes.toStringBinary(startRow));
				scan.setStartRow(startRow);
			}
			ResultScanner scanner = table.getScanner(scan);
			int localRows = 0;
			Result result;
			while((result = scanner.next()) != null){
				System.out.println(localRows++ +":"+result);
				totalRows++;
				lastRow = result.getRow();
			}
			scanner.close();
			if(localRows == 0) break;
		}
		System.out.println("total rows:"+totalRows);
		
		table.close();
	}
}
