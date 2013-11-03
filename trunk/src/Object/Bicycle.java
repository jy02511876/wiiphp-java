package Object;

/**
 * 这个是一个关于自行车的类
 * @author zhoubin
 *
 */
public class Bicycle {
	//自行车的颜色
	private String color;
	//自行车的品牌
	private String brand;
	
	/*
	 * 自行车的主要方法，就是骑嘛。。
	 */
	public String ride()
	{
		return "有一辆"+color+"的"+brand+"嗖的一声骑过";
	}
	
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
}
