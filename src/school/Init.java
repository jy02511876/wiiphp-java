package school;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 初始化数据，制造一个学校的班级，老师，学生数据
 * @author zhoubin
 *
 */
public class Init {
	private Statement statement;
	
	
	public Init(){
		this.statement = Mysql.getSatement();
	}
	
	/**
	 * 学校有3个年级，每个年级有4个班级
	 * @throws SQLException 
	 */
	public void classData() throws SQLException
	{
		//清除表数据
		String sql = "truncate table class";
		statement.execute(sql);
		//3个年级
		for(int grade=1;grade <=3;grade++){
			//4个班级
			for(int classNum=1;classNum <=4;classNum++){
				String className = grade+"年("+classNum+")班";
				sql = "insert into class (class_name) values ('"+className+"')";
//				System.out.println(sql);
				statement.execute(sql);
			}
		}
		System.out.println("班级数据创建成功！");
	}
	
	
	/**
	 * 虽然是老师的数据，这里定义为班主任，这样保证一个班级一个班主任
	 * @throws SQLException 
	 */
	public void teacherData() throws SQLException{
		//清除表数据
		String sql = "truncate table teacher";
		statement.execute(sql);
		//取一共有多少个班级
		sql ="select count(*) from class";
		ResultSet rs = statement.executeQuery(sql);
		rs.first();
		int classCount = rs.getInt(1);
		
		for(int i=0;i<classCount;i++){
			String name = this.getName();
			sql = "insert into teacher (teacher_name,class_id) values ('"+name+"',"+(i+1)+")";
			statement.execute(sql);
		}
		System.out.println("班主任数据创建成功！");
	}
	
	
	/**
	 * 学生数据（造数据，所以需要用到大量的随机数）
	 * 一班级里会有20个学生
	 * @throws SQLException 
	 */
	public void studentData() throws SQLException{
		//清除表数据
		String sql = "truncate table student";
		statement.execute(sql);
				
		//班级数量(写死了，就不从数据库去取了)
		int classCount = 12;
		//学生总数
		int studentCount = 20*classCount;
		//将这2个学生归入不同的班级
		for(int i=0;i<studentCount;i++){
			//随机组姓名
			String name = this.getName();
			//按照一定的比例设置性别
			String sex = this.sex();
			//班级id(取模法，可以打印一下classId，看他的变化规律)
			int classId = i % classCount+1;
			//生日
			String birth = this.birth(classId);
			sql = "insert into student (name,sex,birth,class_id,created) values ('"+name+"','"+sex+"','"+birth+"','"+classId+"',NOW())";
			statement.execute(sql);
		}
		System.out.println("学生数据创建成功！");
	}
	
	
	
	public String getName(){
		String ds = File.separator;
		//姓
		String[] firstNames = new String("赵 钱 孙 李 周 吴 郑 王 冯 陈 楮 卫 蒋 沈 韩 杨 朱 秦 尤 许 何 吕 施 张 孔 曹 严 华 金 魏 陶 姜 戚 谢 邹").split(" ");
		//名（量太大，从文件中读取）
		FileReader nameFile;
		String[] secondNames = new String[]{};
		try {
			nameFile = new FileReader("src"+ds+"school"+ds+"secondName.txt");
			BufferedReader reader = new BufferedReader(nameFile);
			StringBuilder str = new StringBuilder();
			String line;
			while((line=reader.readLine()) != null){
				str.append(line);
			}
			secondNames = str.toString().split("、");
			nameFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//随机取一个姓和名，组成一个名字
		int firstNamesSize = firstNames.length;
		int secondNamesSize = secondNames.length;
		
		StringBuilder name = new StringBuilder();
		name.append(firstNames[(int) (Math.random()*firstNamesSize)]);
		name.append(secondNames[(int) (Math.random()*secondNamesSize)]);
		
		return name.toString();
	}
	
	
	/**
	 * 体现中国特色，男女比例设定为6:4
	 * @return
	 */
	public String sex(){
		//产生一个0-9的随机数
		int num = (int)(Math.random()*10);
		//按照6:4的几率，那么num<=5则为男性
		if(num <= 5){
			return "male";
		}else{
			return "female";
		}
	}
	
	
	/**
	 * 通过班级，来判断对应的年级，通过年级来判断对应的学生年份，因为大年级的学生年龄总比小年级的学生大嘛。
	 * @param classId
	 * @return
	 */
	public String birth(int classId){
		StringBuilder birth = new StringBuilder();

		//设定最大的年份为1980年（即3年级的年份）
		int year = 1980;
		//随机一个月份
		int month = (int) (Math.random()*12)+1;
		//如果月份大于9，那么年份就要减1，（业务问题）
		if(month >=9)
			year--;
		if(classId <=4)
			year = year+2;	//1年级就要小2岁
		else if(classId > 4 && classId <= 8)
			year = year+1;	//2年级就要小1岁
		//生成1-28日，不考虑30，31，还有29，闰年的关系，保证数据正确
		int day = (int) (Math.random() * 27)+1;
		birth.append(year);
		birth.append("-");
		birth.append(month);
		birth.append("-");
		birth.append(day);
		return birth.toString();
	}
	
	
	
	public static void main(String[] args){
		Init init = new Init();
		/*
		String name = init.getName();
		System.out.println(name);
		System.exit(0);
		*/
		try{
			init.classData();
			init.teacherData();
			init.studentData();
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
