package crawler.s.cn;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.apache.hadoop.hive.serde2.columnar.BytesRefWritable;



public class Goods {
	private String title;
	private double price;
	private double delPrice;
	private String url;
	private String imageUrl;
	private List<Object> columns = new ArrayList<Object>();
	
	public Goods(){	
	}
	
	public Goods(String title,double price,double delPrice,String url,String imageUrl){
		setTitle(title);
		setPrice(price);
		setDelPrice(delPrice);
		setUrl(url);
		setImageUrl(imageUrl);
	}
	
	private void initColumns()
	{
		columns.add(url);
		columns.add(price);
		columns.add(delPrice);
		columns.add(imageUrl);
		columns.add(title.replaceAll("\t", ""));
	}
	
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		if(columns.size() == 0)
			initColumns();
		for(int i=0;i<columns.size();i++){
			str.append(String.valueOf(columns.get(i)));
			str.append("\t");
		}
		int lastIndex = str.lastIndexOf("\t");
		return new String(str.substring(0,lastIndex) + "\n");
	}
	
	public BytesRefArrayWritable getItemValue()
	{
		if(columns.size() == 0)
			initColumns();
		BytesRefArrayWritable value = new BytesRefArrayWritable();
		for(int i=0;i<columns.size();i++){
			try {
				value.set(i, new BytesRefWritable(String.valueOf(columns.get(i)).getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	public String getTitle(){
		return title.trim();
	}
	
	public void setTitle(String title){
		this.title = StringUtils.trim(title);
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getDelPrice(){
		return delPrice;
	}
	
	public void setDelPrice(double delPrice){
		this.delPrice = delPrice;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = StringUtils.trim(url);
	}
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl = StringUtils.trim(imageUrl);
	}
	
	
	public static void main(String[] args){
		String title = "耐克nike男鞋板鞋运动鞋555272-616";
		double price = 349.00;
		double delPrice = 499.00;
		String url = "http://www.s.cn/p-0-132-96-0-0-1.html";
		String imageUrl = "http://images.s.cn/images/goods/20130504/c24e7bcd9f1a37b4.jpg";
		
		Goods goods = new Goods(title,price,delPrice,url,imageUrl);
		//oods.getItemValue();
		System.out.println(goods.toString());
	}
}
