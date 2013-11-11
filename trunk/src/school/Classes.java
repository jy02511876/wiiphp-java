package school;

public class Classes {
	private int id;
	private String className;
	private Teacher teacher;
	
	public Classes(String name){
		this.setClassName(name);
	}
	
	public void setTeacher(Teacher teacher){
		this.teacher = teacher;
	}
	
	public Teacher getTeacher(){
		return teacher;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
	public int getId()
	{
		return id;
	}
	
	
	public void setClassName(String name){
		this.className = name;
	}
	
	
	public String getClassName()
	{
		return className;
	}
}
