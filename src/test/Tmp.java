package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;

public class Tmp {
	public static void main(String[] args){
		String date = "2015-12";
		String[] d = date.split("-");
		System.out.println(d[0]+"-"+Integer.parseInt(d[1]));

	}
	

}
