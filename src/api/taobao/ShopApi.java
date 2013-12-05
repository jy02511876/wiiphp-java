package api.taobao;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.ShopGetRequest;
import com.taobao.api.response.ShopGetResponse;

public class ShopApi {
	private final static String appkey = "21694692";
	private final static String secret = "39997a63ef15c861c88cdc06ec391e38";
	private final static String url = "http://gw.api.taobao.com/router/rest";
	
	public static void main(String[] args){
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ShopGetRequest req=new ShopGetRequest();
		req.setFields("sid,cid,title,nick,desc,bulletin,pic_path,created,modified");
		req.setNick("chenpeilian1103");
		try {
			ShopGetResponse response = client.execute(req);
			Shop shop = response.getShop();
			
			System.out.println(shop.getTitle());
			System.out.println(shop.getShopScore());
			System.out.println(shop.toString());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
