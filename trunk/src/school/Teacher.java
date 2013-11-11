package school;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
	private int id;
	private String teacherName;
	private int classId;
	private List<Student> students = new ArrayList<Student> ();
	
	public Teacher(String name,int classId){
		this.setTeacherName(name);
		this.setClassId(classId);
	}
	
	
	public void addStudent(Student student){
		this.students.add(student);
	}
	
	public List<Student> getStudent()
	{
		return students;
	}
	
	
	public void setId(int id){
		this.id = id;
	}
	
	
	public int getId(){
		return id;
	}
	
	
	public void setTeacherName(String name){
		this.teacherName = name;
	}
	
	
	public String getTeacherName(){
		return teacherName;
	}
	
	
	public void setClassId(int id){
		this.classId = id;
	}
	
	
	public int getClassId(){
		return classId;
	}
}
