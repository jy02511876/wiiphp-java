package crawler.s.cn;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.apache.hadoop.hive.serde2.columnar.BytesRefWritable;
import org.apache.log4j.Logger;


public class Shoe {
	private String title;
	private double price;
	private double delPrice;
	private String url;
	private String imageUrl;
	private List<Object> columns = new ArrayList<Object>();
	
	private Logger logger = Logger.getLogger(Shoe.class);
	
	public Shoe(String title,double price,double delPrice,String url,String imageUrl){
		this.title = title.trim();
		this.price = price;
		this.delPrice = delPrice;
		this.url = url;
		this.imageUrl = imageUrl;
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
	
	
	public static void main(String[] args){
		String title = "耐克nike男鞋板鞋运动鞋555272-616";
		double price = 349.00;
		double delPrice = 499.00;
		String url = "http://www.s.cn/p-0-132-96-0-0-1.html";
		String imageUrl = "http://images.s.cn/images/goods/20130504/c24e7bcd9f1a37b4.jpg";
		
		Shoe shoe = new Shoe(title,price,delPrice,url,imageUrl);
		shoe.getItemValue();
	}
}
