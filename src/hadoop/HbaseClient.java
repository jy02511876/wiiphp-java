package hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseClient {
	
	private static Configuration conf;
	private static HBaseAdmin admin;
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		/* 在project/src下有了hbase-site.xml，就不需要自己在代码里写配置了
		conf.set("hbase.master", "cmaster:60000");
		conf.set("hbase.zookeeper.quorum","cmaster");
		conf.set("hbase.zookeeper.property.clientPort","2222");
		conf.set("hbase.master.port", "46637");
		*/
		admin = new HBaseAdmin(conf);
		
		String tableName = "test";
		//createTable("test");
		HTable table = new HTable(conf, tableName);
		System.out.println(table.isAutoFlush());
		
	}
	
	
	
	public static void createTable(String tableName) throws IOException{
		HTableDescriptor htd = new HTableDescriptor(tableName);
		HColumnDescriptor hcd = new HColumnDescriptor("data");
		
		htd.addFamily(hcd);
		admin.createTable(htd);
		byte [] tablename = htd.getName();
		HTableDescriptor [] tables = admin.listTables();
		if(tables.length != 1 && Bytes.equals(tablename, tables[0].getName())){
			admin.close();
			throw new IOException("Failed create of table");
		}
		admin.close();
	}
}
