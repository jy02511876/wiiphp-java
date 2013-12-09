package s_cn.crawler;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.s.cn.ShoeCrawler;
import s_cn.Goods;

public class CrawlerByJsoup {
	private static String postUrl = "http://www.s.cn/newsearch-get_ajax_index.html";
	private String filename;
	
	Logger logger = Logger.getLogger(ShoeCrawler.class);
	
	public CrawlerByJsoup(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		filename = "files/s_"+date;
	}
	
	public List<Goods> crawler() throws IOException{
		List<Goods> goods = new ArrayList<Goods>();

		FileWriter file = new FileWriter(filename);
		BufferedWriter writer = new BufferedWriter(file);
		
		int page = 0;
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
				logger.info("page:"+page+",size:"+dls.size());
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
					
					Goods s = new Goods(title,Double.parseDouble(price),Double.parseDouble(delPrice),url,imageUrl);
					//goods.add(s);
					writer.write(s.toString());
					writer.newLine();
					writer.flush();
				}
				
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

		writer.close();
		file.close();

		return goods;
	}
	
	
	
	public static void main(String[] args){
		CrawlerByJsoup crawler = new CrawlerByJsoup();
		try {
			crawler.crawler();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
