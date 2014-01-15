package s_cn.save;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import s_cn.Goods;
import s_cn.utils.Md5;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

import crawler.util.MongoDB;

public class SaveToMongo {
	private static String folder = "files/s_cn";
	private static String dbname = "test";
	private static String listTable = "s_cn_list";
	private static String hisTable = "s_cn_his";
	
	
	/**
	 * 将新抓来的数据，放入全局库和历史库
	 * @throws IOException
	 */
	public void addNew() throws IOException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		String dailyFileName = folder+"/s_"+date;
		File f = new File(dailyFileName);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		DB db = MongoDB.getDB(dbname);
		DBCollection listCollection = db.getCollection(listTable);
		DBCollection hisCollection = db.getCollection(hisTable);

		String line;
		BasicDBObject q;
		StringBuilder titleBuilder = new StringBuilder();
		int j = 0;
		while((line = reader.readLine()) != null){
			//System.out.println(line);
			StrTokenizer tokenizer = new StrTokenizer(line);
			BasicDBObjectBuilder obj = BasicDBObjectBuilder.start();

			String[] data = tokenizer.getTokenArray();
			//System.out.println(data.length);
			if(data.length == 0) continue;
			
			for(int i=4;i<data.length;i++){
				titleBuilder.append(data[i]+"\t");
			}
			String title = StringUtils.trim(titleBuilder.toString());
			String url = data[0];
			String imageUrl = data[3];
			Double price = Double.parseDouble(data[1]);
			Double delPrice = Double.parseDouble(data[2]);
			
			Goods goods = new Goods(title,price,delPrice,url,imageUrl);
			//System.out.println(goods.toString());
			
			//先查找一下商品是否已经在全局库中，不在的话，插入新数据
			q = new BasicDBObject("url",goods.getUrl());
			long count = listCollection.getCount(q);
			if(count == 0){
				obj.append("title", goods.getTitle())
					.append("url", goods.getUrl())
					.append("image_url", goods.getImageUrl());
				listCollection.insert(obj.get(),WriteConcern.ERRORS_IGNORED);
				System.out.println("new:"+goods.getTitle());
			}
			
			//插入到历史表中
			BasicDBObject hisObj = new BasicDBObject();
			hisObj.append("url", Md5.Generate(goods.getUrl()))
					.append("price", goods.getPrice())
					.append("del_price", goods.getDelPrice())
					.append("date", date);
			hisCollection.insert(hisObj,WriteConcern.ERRORS_IGNORED);
			
			j++;
			System.out.println(j);
		}
		MongoDB.close();
	}
	
	
	/**
	 * 导入历史的全局数据
	 * 其中有一些数据，抓回来的时候，出现了编码问题，需要重新抓一下title
	 */
	public void initData(){
		DB db = MongoDB.getDB(dbname);
		try {
			//importAll(db);
			repair(db);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MongoDB.close();
	}
	
	
	/**
	 * 初始化全局的数据
	 * @throws IOException
	 */
	private void importAll(DB db) throws IOException{
		File f = new File(folder+"/s_list_2013-12-09");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

		DBCollection listCollection = db.getCollection(listTable);
		listCollection.ensureIndex(new BasicDBObject("url", -1), "url", true);
		
		DBCollection hisCollection = db.getCollection(hisTable);
		hisCollection.ensureIndex(new BasicDBObject("date",-1).append("url", 1),"date_url_index",true);
		
		String line;
		int i = 1;
		while((line = reader.readLine()) != null){
			//System.out.println(line);
			
			StrTokenizer tokenizer = new StrTokenizer(line,",");
			BasicDBObjectBuilder obj = BasicDBObjectBuilder.start();
			String[] data = tokenizer.getTokenArray();
			if(data.length != 6) continue;
			String title = data[0];
			String url = data[2];
			String imageUrl = data[1];
			Double price = Double.parseDouble(data[3]);
			Double delPrice = Double.parseDouble(data[4]);
			String date = data[5];
			
			Goods goods = new Goods(title,price,delPrice,url,imageUrl);
			System.out.println(goods.toString());
			obj.append("title", goods.getTitle())
				.append("url", goods.getUrl())
				.append("image_url", goods.getImageUrl());
			listCollection.insert(obj.get(),WriteConcern.ERRORS_IGNORED);
			
			BasicDBObject hisObj = new BasicDBObject("url", Md5.Generate(goods.getUrl()))
				.append("price", goods.getPrice())
				.append("del_price", goods.getDelPrice())
				.append("date", date);
			hisCollection.insert(hisObj,WriteConcern.ERRORS_IGNORED);
			
			System.out.println(i);
			i++;
			
		}
		
	}
	
	
	/**
	 * 有部分数据title乱码了，重新抓取覆盖一下
	 * @throws IOException
	 */
	private void repair(DB db) throws IOException{
		DBCollection collection = db.getCollection(listTable);
		BasicDBObject query = new BasicDBObject("title",new BasicDBObject("$regex","\\?"));
		DBCursor cursor = collection.find(query);
		DBObject obj;
		BasicDBObject q;
		while(cursor.hasNext()){
			obj = cursor.next();
			String url = obj.get("url").toString();
			Document doc = Jsoup.connect(url)
					.timeout(5000)
					.get();
			System.out.println(obj.get("title"));
			String title = doc.select("h1.goodsname").text();
			System.out.println(title);
			obj.put("title", title);
			q = new BasicDBObject("_id",obj.get("_id"));
			collection.update(q, obj);
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		SaveToMongo mongo = new SaveToMongo();
		//导入数据,部分数据的title有问题，显示 ？？，特殊处理一下
		//mongo.initData();
		
		//将每天抓的数据中的新的商品，导入mongo中
		mongo.addNew();
	}
	
	
	
}
