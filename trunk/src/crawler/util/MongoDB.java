package crawler.util;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDB {
	private static String user = "root";
	private static String password = "123456";
	private static String host = "cmaster";
	private static String port = "27017";
	
	private static MongoClient mongo = null;
	private static DB db = null;
	
	public static void initMongo(){
		if(mongo == null){
			String uri ="mongodb://"+user+":"+password+"@"+host+":"+port;
			MongoClientURI URI = new MongoClientURI(uri);
			try {
				mongo = new MongoClient(URI);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static DB getDB(String DbName){
		if(mongo == null)
			initMongo();
		return mongo.getDB(DbName);
	}
	
	
	public static void close(){
		mongo.close();
	}
	
	public static void main(String[] args){
		
		DB db = MongoDB.getDB("test");
		DBCollection collection = db.getCollection("s_cn_list");
		BasicDBObject obj = new BasicDBObject("title","title")
								.append("url", "http")
								.append("image_url", "http://image");
		collection.insert(obj);
		MongoDB.close();
		System.out.println("finish");
	}
}
