package test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.hadoop.hbase.util.Bytes;



public class Md5Test {
	public static final int MD5_LENGTH = 16; 
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String str = "admin";
		MessageDigest adapter = MessageDigest.getInstance("Md5");
		System.out.println(String.valueOf(adapter.digest(Bytes.toBytes(str))));
//		String md5 = new String(adapter.digest(str.getBytes()));
		//System.out.println(convertToHexString(adapter.digest(str.getBytes())));
	}
	
	
	static String convertToHexString(byte data[]) {
	  StringBuffer strBuffer = new StringBuffer();
	  for (int i = 0; i < data.length; i++) {
	   strBuffer.append(Integer.toHexString(0xff & data[i]));
	  }
	  return strBuffer.toString();
	 }
	
	
	
}
