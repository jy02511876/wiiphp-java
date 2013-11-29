package base.type;

import java.io.UnsupportedEncodingException;

public class ByteCharTest {
	public static void main(String[] args){
		String s1 = "m";
		String s2 = "我";
		
		System.out.println("default code:\t length of s1 is "+s1.getBytes().length);
		System.out.println("default code:\t lenght of s2 is "+s2.getBytes().length);
		
		try {
			System.out.println("GBK code:\t length of s1 is "+s1.getBytes("GBK").length);
			System.out.println("GBK code:\t lenght of s2 is "+s2.getBytes("GBK").length);
			
			System.out.println("unicode code:\t lenght of s1 is "+ s1.getBytes("Unicode").length);
			System.out.println("unicode code:\t lenght of s2 is "+ s2.getBytes("Unicode").length);
			
			System.out.println("UTF-8 code:\t lenght of s1 is "+s1.getBytes("UTF-8").length);
			System.out.println("UTF-8 code:\t lenght of s2 is "+s2.getBytes("UTF-8").length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
