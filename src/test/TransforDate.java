package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransforDate {
	public static void main(String[] args) throws ParseException{		
		String str = "[17/Mar/2014:14:00:52 +0800]";
		SimpleDateFormat format = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss Z]",Locale.ENGLISH);
		Date d = format.parse(str);
		System.out.println(d.toString());
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String d2 = format1.format(d);
		System.out.println(d2);
	}
}
