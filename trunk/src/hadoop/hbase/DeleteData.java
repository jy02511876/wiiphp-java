package hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class DeleteData {
	private static Configuration conf;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		
		HTable table = new HTable(conf, "testtable");
		
		byte[] row = Bytes.toBytes("myrow-1");
		byte[] family = Bytes.toBytes("colfam1");
		byte[] qual = Bytes.toBytes("q1");
		Delete delete = new Delete(row);
		
		delete.deleteFamily(Bytes.toBytes("colfam1"));
		delete.deleteColumn(family, qual);
		table.delete(delete);
		System.out.println("deleted");
		
		table.close();
	}
}
