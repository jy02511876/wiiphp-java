package Object;

public class Car {
	private String color;
	private String brand;
	private String size;
	
	public String travel()
	{
		return "有一辆" + color + "的" + brand + "的" + size + "呼的一声开过";
		
	}
	public String stop()
	{
		return color + brand + size + "停在路边";
	}
	
	
	public void setColor (String color) {
		this.color = color;
	}
	
	public void setBrand (String brand) {
		this.brand = brand;
	}
	
	public void setSize (String size) {
		this.size = size;
	}
	

}
