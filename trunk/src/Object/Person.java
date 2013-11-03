package Object;

/**
 * 路人的类，
 * 里面包含描述路人的属性（名字，衣服颜色，性别等等）,
 * 还有路人的能有的方法，最主要是的行走(walk)，另外可以加一些其他的，比如坐在路边
 * 
 * @author zhoubin
 *
 */
public class Person {
	//人的名字
	private String name = null;
	//人的颜色（主要指衣服的颜色）
	private String color = null;
	//人的性别，默认为男性
	private String sex = "male";
	private int age;
	
	
	/**
	 * 上帝会看到的人的主要方法
	 * @return
	 */
	public String walk()
	{
		String sexStr = "";
		if(sex == "male"){
			sexStr = "男";
		}else{
			sexStr = "女";
		}
		return "有一个叫\""+name+"\"的"+sexStr+"人，穿着"+color+"的衣服，在路上行走";
	}
	
	/**
	 * 坐在路边的话，上帝就知道他的名字，性别和衣服颜色就看不清除了。
	 * @param name
	 */
	public String sit()
	{
		return name+"做在路边";
	}
	
	
	/**
	 * 设置这个人的名字
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	
	public void setColor(String color){
		this.color = color;
	}
	
	
	public void setSex(String sex){
		this.sex = sex;
	}
}
