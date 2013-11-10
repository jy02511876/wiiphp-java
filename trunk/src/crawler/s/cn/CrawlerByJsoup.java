package crawler.s.cn;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerByJsoup {
	private static String postUrl = "http://www.s.cn/newsearch-get_ajax_index.html";
	
	Logger logger = Logger.getLogger(ShoeCrawler.class);
	
	
	public List<Shoe> crawler(){
		List<Shoe> goods = new ArrayList<Shoe>();
		int page = 0;
		
		FileOutputStream outSTr = null;
		BufferedOutputStream Buff=null;
		
		try {
			outSTr = new FileOutputStream(new File("E:/s.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Buff=new BufferedOutputStream(outSTr);
		
		while(page >=0){
			try {
				Document doc = Jsoup.connect(postUrl)
						.data("ajax","ajax")
						.data("args","a:1:{s:8:\"keywords\";a:2:{s:5:\"value\";s:0:\"\";s:9:\"clear_url\";s:22:\"http://www.s.cn/p.html\";}}")
						.data("orderBy","0")
						.data("query_url","http://www.s.cn/p.html")
						.data("search_filters","a:1:{s:8:\"keywords\";s:0:\"\";}")
						.data("page",String.valueOf(page))
						.userAgent("Mozilla")
						.timeout(3000)
						.post();
				
				Elements dls = doc.select("dl");
				if(dls.size() != 0)
					page++;
				else
					page = -1;
				for(Element dl : dls){
					String title = dl.select("img").first().attr("alt").trim();
					String price = dl.select("i.price").first().html().substring(1);
					String delPrice = dl.select("i.del_price").first().html().substring(1);
					String url = dl.select("a").first().attr("href");
					String imageUrl = dl.select("img").first().attr("src");
					
					Shoe s = new Shoe(title,Double.parseDouble(price),Double.parseDouble(delPrice),url,imageUrl);
					//goods.add(s);
					try {
						

							Buff.write(s.toString().getBytes());
						
						Buff.flush();
						
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				logger.info("page:"+page+",size:"+dls.size());
				try {
					Thread.sleep(1000*2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//page = -1;
			}
		}
		try {
			Buff.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outSTr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goods;
	}
	
	
	
	public static void main(String[] args){
		CrawlerByJsoup crawler = new CrawlerByJsoup();
		List<Shoe> goods = crawler.crawler();
		/*
		FileOutputStream outSTr = null;
		BufferedOutputStream Buff=null;
		try {
			outSTr = new FileOutputStream(new File("E:/s.txt"));
			Buff=new BufferedOutputStream(outSTr);
			for(Shoe g : goods){
				Buff.write(g.toString().getBytes());
			}
			Buff.flush();
			Buff.close();
			outSTr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
