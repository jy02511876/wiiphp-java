package hadoop.hbase.logs.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MobileService {
	public String datetime;
	public String ip;
	public String uid;
	public String udid;
	public String request;
	public String api;
	public String input;
	public String output;
	
	
	
	public MobileService(String datetime, String ip, String uid, String udid,
			String request, String api, String input, String output) {
		this.datetime = datetime;
		this.ip = ip;
		this.uid = uid;
		this.udid = udid;
		this.request = request;
		this.api = api;
		this.input = input;
		this.output = output;
	}

	public long toUnixTime(){
		long time = 0L;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
		Date d;
		try {
			d = format.parse(getDatetime());
			time = d.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return time;
	}
	
	private String convertToHexString(byte data[]) {
		  StringBuffer strBuffer = new StringBuffer();
		  for (int i = 0; i < data.length; i++) {
		   strBuffer.append(Integer.toHexString(0xff & data[i]));
		  }
		  return strBuffer.toString();
	}

	
	public String getKey() {
		String key = "";
		String md5str = getIp()+getRequest()+getInput()+getOutput();
		MessageDigest adapter;
		try {
			adapter = MessageDigest.getInstance("Md5");
			key = getUid()+"_"+toUnixTime()+"_"+convertToHexString(adapter.digest(md5str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
	


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getUdid() {
		return udid;
	}


	public void setUdid(String udid) {
		this.udid = udid;
	}


	public String getApi() {
		return api;
	}


	public void setApi(String api) {
		this.api = api;
	}
	
	
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
}
