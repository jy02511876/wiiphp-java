package s_cn.save;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import s_cn.Goods;

public class SavePriceHisToHbase {
	
	public static Configuration conf;
	public static HBaseAdmin admin = null;
	
	public static void init() throws IOException{
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.2.200");
		conf.set("hbase.zookeeper.property.clientport", "2222");
		admin = new HBaseAdmin(conf);
		HConnection conn = admin.getConnection();
		HTableDescriptor[] tableNames = conn.listTables();
		for(HTableDescriptor table : tableNames){
			System.out.println(table.getNameAsString());
		}
		System.exit(0);
	}
	
	
	public static void createTable(String tableName,HColumnDescriptor... column) throws IOException{
		if(admin.tableExists(tableName)){
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println(tableName+" deleted");
		}
		HTableDescriptor table = new HTableDescriptor(tableName);
		for(HColumnDescriptor col : column){
			table.addFamily(col);
		}
		admin.createTable(table);
	}
	
	
	public static void close() throws IOException{
		if(admin != null)
			admin.close();
	}
	
	
	
	public static void save(HTable table,Goods goods) throws NoSuchAlgorithmException, IOException{
		Put put = new Put(MD5(goods.getUrl()).getBytes());
		put.add(new String("price").getBytes(),null,String.valueOf(goods.getPrice()).getBytes());
		put.add(new String("del_price").getBytes(),null,String.valueOf(goods.getDelPrice()).getBytes());
		table.put(put);
	}
	
	
	
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException{
		init();
		HColumnDescriptor price = new HColumnDescriptor("price");
		HColumnDescriptor delPrice = new HColumnDescriptor("del_price");
		createTable("s_cn",price,delPrice);
		
		HTable table = new HTable(conf,"s_cn");
		//HTableDescriptor table = admin.getTableDescriptor(new String("s_cn").getBytes());
		String key = new String("http://aaa.com");
		
		Put put = new Put(MD5(key).getBytes());
		put.add(new String("price").getBytes(), null, new String("100").getBytes());
		put.add(new String("del_price").getBytes(), null, new String("150").getBytes());
		
		table.put(put);
		close();
	}
	
	
	public static String MD5(String str) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		return convertToHexString(digest.digest(str.getBytes()));
	}
	
	public static String convertToHexString(byte data[]) {
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			strBuffer.append(Integer.toHexString(0xff & data[i]));
		}
		return strBuffer.toString();
	}
}
