package hadoop.hbase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.hadoop.hbase.util.Bytes;




public class RowKeyTest {
	public static void main(String[] args){
		String name = "az";
		String kame = "123";
		byte[] n = mkRowKey(name);
		byte[] k = mkRowKey(name,kame);
		
		System.out.println(to_str(n));
		System.out.println(to_str(k));
		
		
		byte[] nameByte = Bytes.toBytes(name);
		System.out.println(Bytes.toString(nameByte));
		
		byte[] rowKey = mkRowKey("zhoubin");
		System.out.println(to_str(rowKey));
	}
	
	/*
	private static byte[] mkRowKey(String user) {
		byte[] userHash = md5sum(user);
		byte[] timestamp = Bytes.toBytes(-1 * 1398516250);
		byte[] rowKey = new byte[24];
		int offset = 0;
	    offset = Bytes.putBytes(rowKey, offset, userHash, 0, userHash.length);
	    Bytes.putBytes(rowKey, offset, timestamp, 0, timestamp.length);
	    return rowKey;
	}
	*/
	
	public static byte[] mkRowKey(String a) {
	    byte[] ahash = md5sum(a);
	    byte[] rowkey = new byte[32];

	    Bytes.putBytes(rowkey, 0, ahash, 0, ahash.length);
	    return rowkey;
	  }

	  public static byte[] mkRowKey(String a, String b) {
	    byte[] ahash = md5sum(a);
	    byte[] bhash = md5sum(b);
	    byte[] rowkey = new byte[32];

	    int offset = 0;
	    offset = Bytes.putBytes(rowkey, offset, ahash, 0, ahash.length);
	    Bytes.putBytes(rowkey, offset, bhash, 0, bhash.length);
	    return rowkey;
	  }
	  
	  
	
	public static byte[] md5sum(String s){
		MessageDigest d;
	    try {
	      d = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	      throw new RuntimeException("MD5 algorithm not available!", e);
	    }

	    return d.digest(Bytes.toBytes(s));
	}
	
	
	
	
	private static String to_str(byte[] xs) {
	    StringBuilder sb = new StringBuilder(xs.length *2);
	    for(byte b : xs) {
	      sb.append(b).append(" ");
	    }
	    sb.deleteCharAt(sb.length() -1);
	    return sb.toString();
	  }
}
