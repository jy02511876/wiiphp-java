package s_cn.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static String Generate(String str){
		String res = null;
		MessageDigest adapter;
		try {
			adapter = MessageDigest.getInstance("Md5");
			res = convertToHexString(adapter.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
	public static String convertToHexString(byte data[]) {
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			strBuffer.append(Integer.toHexString(0xff & data[i]));
		}
		return strBuffer.toString();
	}
}
