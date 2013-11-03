package school;

import java.util.HashMap;
import java.util.Map;

public class Person {
	public static void main(String[] args){
		
		
		String[] name = new String[]{"zhoubin","dongchen","rugao"};
		
		Map<String,String> m = new HashMap<String,String>(){};
		for(int i= 0;i<10;i++){
			String key = "age"+i;
			String value = "年龄"+i;
			m.put(key,value);
			String key2 = "name"+i;
			String value2 = "xxx"+i;
			m.put(key2,value2);
		}
		
		for(int i= 0;i<10;i++){
			String key = "age"+i;
			System.out.println(m.get(key));
			System.out.println(m.get("name"+i));
		}

		
		//System.out.println(m.get("name1"));
		System.exit(0);
		
		
	}
}


class Zhoubin{
	public String name = "zhoubin";
	public int age = 27;
	private String sex = "male";
	
	public String getSex(){
		return this.sex;
	}

	public void setSex(String s){
		this.sex = s;
	}
}

class zhoubing{
	int month = 10;
}