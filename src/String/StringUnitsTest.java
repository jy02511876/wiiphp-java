package String;

import org.apache.commons.lang.StringUtils;



public class StringUnitsTest {

	public static void println(String str){
		System.out.println(str);
	}
	public static void main(String[] args){
		String name1 = "\t Peter \n";
		String name2 = " Pete r ";
		println(name1);
		println(name2);
		println(StringUtils.trim(name1));
		println(StringUtils.trim(name2));
		println(StringUtils.strip(name1));
		println(StringUtils.strip(name2));
		println(StringUtils.strip(name2,"r "));
	}
}
