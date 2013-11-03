package Object;

import java.util.ArrayList;

public class Road {
	//路名
	private String name;
	//路人数组
	private ArrayList<Person> person = new ArrayList<Person>();
	//自行车数组
	private ArrayList<Bicycle> bicycle = new ArrayList<Bicycle>();
	//汽车数组

	
	/**
	 * 构造方法，初始化这条路的路名
	 * @param name
	 */
	public Road(String name){
		this.name = name;
	}
	
	/**
	 * 返回路上能看到的事和物
	 * @return
	 */
	public String things()
	{
		//初始化，准备一下数据
		this.init();
		StringBuilder output = new StringBuilder();
		output.append("在"+name+"上，有:\n");
		
		//路人(循环数组的第一种方法)
		for(int i=0;i<person.size();i++){
			output.append("\t");
			//第1个人，让他坐着，后面的人，都是在走路
			if(i == 0){
				output.append(person.get(i).sit());
			}else{
				output.append(person.get(i).walk());
			}
			output.append("\n");
		}
		
		//自行车（循环数组的第2中方法）
		for(Bicycle bike : bicycle){
			output.append("\t"+bike.ride()+"\n");
		}
		
		//汽车
		
		return output.toString();
	}
	
	
	/**
	 * 初始化，主要是给具体的数据，真实情况应该是从数据库中取出
	 */
	private void init()
	{
		//建立第1个人
		Person person1 = new Person();
		person1.setName("周斌");
		person1.setColor("白色");
		person1.setSex("male");
		//建立第2个人
		Person person2 = new Person();
		person2.setName("董晨");
		person2.setColor("红色");
		person2.setSex("male");
		//建立第3个人
		Person person3 = new Person();
		person3.setName("美屡");
		person3.setColor("绿色");
		person3.setSex("female");
		//将这3个放入路人的数组里
		person.add(person1);
		person.add(person2);
		person.add(person3);
		
		//建立第1辆自行车
		Bicycle bicycle1 = new Bicycle();
		bicycle1.setColor("蓝色");
		bicycle1.setBrand("捷安特");
		//建立第2辆自行车
		Bicycle bicycle2 = new Bicycle();
		bicycle2.setColor("黄色");
		bicycle2.setBrand("美能达");
		//将这2辆自行车放入数组
		bicycle.add(bicycle1);
		bicycle.add(bicycle2);
		
		//建立第1辆汽车
	}
}
