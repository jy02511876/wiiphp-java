package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileIO {
	public static void copyFile(String inName,String outName) throws IOException{
		BufferedInputStream is = 
				new BufferedInputStream(new FileInputStream(inName));
		BufferedOutputStream os = 
				new BufferedOutputStream(new FileOutputStream(outName));
		copyFile(is,os,true);
	}
	
	public static void copyFile(InputStream is, OutputStream os, boolean close) throws IOException{
		int b;
		while((b = is.read()) != -1){
			os.write(b);
		}
		is.close();
		if(close)
			os.close();
	}
	
	
	public static String renderToString(String fileName) throws IOException
	{
		BufferedInputStream is = 
				new BufferedInputStream(new FileInputStream(fileName));
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[8192];
		int n;
		int off = 0;
		
		while((n=is.read(b,off,8192)) > 0){
			sb.append(n);
			off+= 8192;
		}
		is.close();
		return sb.toString();
	}
}
