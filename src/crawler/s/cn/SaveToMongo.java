package crawler.s.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import crawler.util.MongoDB;

public class SaveToMongo {
	
	private static String filename = "files/s_list";
	private static String dailyFileName = "files/s.txt";
	private static String dbname = "test";
	private static String table = "s_cn_list";
	
	public static void save() throws IOException{
		File f = new File(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		DB db = MongoDB.getDB(dbname);
		DBCollection collection = db.getCollection(table);
		collection.ensureIndex(new BasicDBObject("url", -1), "url", true);
		
		String line;
		int i = 1;
		while((line = reader.readLine()) != null){
			System.out.println(line);
			
			StrTokenizer tokenizer = new StrTokenizer(line,",");
			BasicDBObjectBuilder obj = BasicDBObjectBuilder.start();
			String[] data = tokenizer.getTokenArray();
			if(data.length != 3) continue;
			String title = data[0];
			String url = data[2];
			String imageUrl = data[1];
			Goods goods = new Goods();
			goods.setTitle(title);
			goods.setUrl(url);
			goods.setImageUrl(imageUrl);
			
			obj.append("title", goods.getTitle())
				.append("url", goods.getUrl())
				.append("image_url", goods.getImageUrl());
			collection.insert(obj.get(),WriteConcern.ERRORS_IGNORED);
			System.out.println(i);
			i++;
			
		}
		MongoDB.close();
	}
	
	
	public static void repair() throws IOException{
		DB db = MongoDB.getDB(dbname);
		DBCollection collection = db.getCollection(table);
		BasicDBObject query = new BasicDBObject("title",new BasicDBObject("$regex","\\?"));
		DBCursor cursor = collection.find(query);
		DBObject obj;
		BasicDBObject q;
		while(cursor.hasNext()){
			obj = cursor.next();
			String url = obj.get("url").toString();
			Document doc = Jsoup.connect(url)
					.timeout(3000)
					.get();
			System.out.println(obj.get("title"));
			String title = doc.select("h1.goodsname").text();
			System.out.println(title);
			obj.put("title", title);
			q = new BasicDBObject("_id",obj.get("_id"));
			collection.update(q, obj);
		}
		MongoDB.close();
	}
	
	
	
	
	public static void addNew() throws IOException{
		File f = new File(dailyFileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		DB db = MongoDB.getDB(dbname);
		DBCollection collection = db.getCollection(table);

		String line;
		int i = 1;
		BasicDBObject q;
		while((line = reader.readLine()) != null){
			StrTokenizer tokenizer = new StrTokenizer(line);
			BasicDBObjectBuilder obj = BasicDBObjectBuilder.start();
			String[] data = tokenizer.getTokenArray();
			if(data.length != 5) continue;
			String title = data[4];
			String url = data[0];
			String imageUrl = data[3];
			Goods goods = new Goods();
			goods.setTitle(title);
			goods.setUrl(url);
			goods.setImageUrl(imageUrl);
			
			//先查找一下商品是否已经在库中，不在的话，插入新数据
			q = new BasicDBObject("url",goods.getUrl());
			long count = collection.getCount(q);
			if(count == 0){
				obj.append("title", goods.getTitle())
					.append("url", goods.getUrl())
					.append("image_url", goods.getImageUrl());
				collection.insert(obj.get(),WriteConcern.ERRORS_IGNORED);
				System.out.println("new:"+goods.getTitle());
			}
			
			System.out.println(i);
			i++;
		}
		MongoDB.close();
	}
	
	
	
	public static void main(String[] args) throws IOException {
		//导入数据
		//save();
		
		//部分数据的title有问题，显示 ？？，特殊处理一下
		//repair();
		
		//将每天抓的数据中的新的商品，导入mongo中
		addNew();
	}
	
	
	
}
