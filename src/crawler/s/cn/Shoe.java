package crawler.s.cn;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.apache.log4j.Logger;


public class Shoe {
	private String title;
	private double price;
	private double delPrice;
	private String url;
	private String imageUrl;
	private List<String> columns;
	
	private Logger logger = Logger.getLogger(Shoe.class);
	
	public Shoe(String title,double price,double delPrice,String url,String imageUrl){
		this.title = title;
		this.price = price;
		this.delPrice = delPrice;
		this.url = url;
		this.imageUrl = imageUrl;
	}
	
	private void initColumns()
	{
		System.out.println(title);
		columns.add(title);
		System.out.println(columns.get(0));
		System.exit(0);
		/*
		columns.add(String.valueOf(price));
		columns.add(String.valueOf(delPrice));
		columns.add(url);
		columns.add(imageUrl);
		*/
	}
	
	
	public BytesRefArrayWritable getItemValue()
	{
		initColumns();
		System.out.println(columns.isEmpty());
		System.exit(0);
		if(columns.size() == 0)
			this.initColumns();
		BytesRefArrayWritable value = new BytesRefArrayWritable();
		
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
