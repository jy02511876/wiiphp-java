package school;


public class Student {
	private int id;
	private String name;
	private String sex;
	private String birth;
	private String created;
	private String teacherName;
	private String className;
	
	//构造方法
	public Student(String name,String sex,String birth,String created){
		this.setName(name);
		this.setSex(sex);
		this.setBirth(birth);
		this.setCreated(created);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
	public int getId(){
		return id;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public String getName(){
		return name;
	}
	
	
	public void setSex(String sex){
		this.sex = sex;
	}
	
	
	public String getSex(){
		return sex;
	}
	
	public void setBirth(String birth){
		this.birth = birth;
	}
	
	
	public String getBirth(){
		return birth;
	}
	
	
	public void setCreated(String created){
		this.created = created;
	}
	
	
	public String getCreated(){
		return created;
	}
	
	
	public void setTeacher(String teacherName){
		this.teacherName = teacherName;
	}
	
	public String getTeacher(){
		return teacherName;
	}
	
	public void setClassName(String className){
		this.className = className;
	}
	
	public String getClassName(){
		return className;
	}
}
