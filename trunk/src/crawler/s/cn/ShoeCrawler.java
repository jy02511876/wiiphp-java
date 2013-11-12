package crawler.s.cn;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class ShoeCrawler {
	private static String postUrl = "http://www.s.cn/newsearch-get_ajax_index.html";
	
	Logger logger = Logger.getLogger(ShoeCrawler.class);
	
	public List<Goods> crawler(){
		List<Goods> shoes = new ArrayList<Goods>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(postUrl);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		
		int page = 0;
		while(page >=0){
			nvps.add(new BasicNameValuePair("ajax","ajax"));
			nvps.add(new BasicNameValuePair("args","a:1:{s:8:\"keywords\";a:2:{s:5:\"value\";s:0:\"\";s:9:\"clear_url\";s:22:\"http://www.s.cn/p.html\";}}"));
			nvps.add(new BasicNameValuePair("orderBy","0"));
			nvps.add(new BasicNameValuePair("page","0"));
			nvps.add(new BasicNameValuePair("query_url","http://www.s.cn/p.html"));
			nvps.add(new BasicNameValuePair("search_filters","a:1:{s:8:\"keywords\";s:0:\"\";}"));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity entity = httpResponse.getEntity();
				
				EntityUtils.consume(entity);
				logger.info("page:"+page+",status:"+httpResponse.getStatusLine());	
				page = -1;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				page = -1;
			}
		}
		
		return shoes;
	}
	
	
	
}
