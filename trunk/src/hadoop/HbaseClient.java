package hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseClient {
	public static void main(String[] args) throws IOException{
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor htd = new HTableDescriptor("test");
		HColumnDescriptor hcd = new HColumnDescriptor("data");
		
		htd.addFamily(hcd);
		admin.createTable(htd);
		byte [] tablename = htd.getName();
		HTableDescriptor [] tables = admin.listTables();
		if(tables.length != 1 && Bytes.equals(tablename, tables[0].getName())){
			throw new IOException("Failed create of table");
		}
	}
}
