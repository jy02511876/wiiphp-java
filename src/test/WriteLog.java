package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteLog {
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-H");
		FileOutputStream out = null;
		try {
			String filename = args[0]+"mobile_service.log."+sdf.format(new Date());
			out = new FileOutputStream(new File(filename));
			while(true){
				long begin = System.currentTimeMillis();   
				out.write(new String(String.valueOf(begin)+" log "+args[0]+"\r\n").getBytes());
				try {
					Thread.sleep(1000*2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
}
