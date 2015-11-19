package hadoop.hbase.logs;


import hadoop.hbase.logs.model.MobileService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class Import {
	private static Configuration conf;
	private static HTable table;
	
	
	public static void readFile(String filename) throws IOException, NoSuchAlgorithmException{
		BufferedReader br = new BufferedReader(new InputStreamReader(  
                new FileInputStream(filename)));  
		int i = 0;
		int count = 0;
        for (String line = br.readLine(); line != null; line = br.readLine()) {  
        	i++;
        	//System.out.print(i);
        	String datetime,ip,uid,udid,request,api,input,output;
        	String[] list = line.split("\t");
			if(list.length != 8){
				System.out.println(line);
				System.out.println(list.length);
				//System.out.println(list.get(2).toString());
				System.exit(0);
			}
			
			uid = list[2];
			if(uid.length() == 0){
				continue;
			}
			datetime = list[0];
			ip = list[1];
			udid = list[3];
			request = list[4];
			api = list[5];
			input = list[6];
			output = list[7];
			
			MobileService ms = new MobileService(datetime,ip,uid,udid,request,api,input,output);
			if(insert(ms)){
				count++;
				System.out.println(count);
			}
        }  
        br.close();  
	}
	

	public static boolean insert(MobileService ms){
		String key = ms.getKey();
		if(key.length() != 0){
			Put put = new Put(Bytes.toBytes(ms.getKey()));

			put.add(Bytes.toBytes("datetime"),Bytes.toBytes(""),Bytes.toBytes(ms.getDatetime()));
			put.add(Bytes.toBytes("ip"),Bytes.toBytes(""),Bytes.toBytes(ms.getIp()));
			put.add(Bytes.toBytes("request"),Bytes.toBytes(""),Bytes.toBytes(ms.getRequest()));
			put.add(Bytes.toBytes("api"),Bytes.toBytes(""),Bytes.toBytes(ms.getApi()));
			put.add(Bytes.toBytes("input"),Bytes.toBytes(""),Bytes.toBytes(ms.getInput()));
			put.add(Bytes.toBytes("output"),Bytes.toBytes(""),Bytes.toBytes(ms.getOutput()));
			try {
				table.put(put);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public static void main(String[] args) throws IOException{
		conf = HBaseConfiguration.create();
		table = new HTable(conf, "mobile_service");
		//table.setAutoFlush(false);
		
		File file = new File(args[0]);
		//列出目录下的所有文件
		String[] list = file.list();
		for(String filename : list){
			try {
				readFile(args[0]+File.separator+filename);
			} catch (IOException | NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		table.close();
	}
	
	
	
}
