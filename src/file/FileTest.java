package file;

import java.io.File;

public class FileTest {
	public static void main(String[] args){
		String pathName = "files";
		File file = new File(pathName);
		
		System.out.println("filename:"+file.getName());
		System.out.println("parent name:"+file.getParent());
		System.out.println("绝对路径 name:"+file.getAbsolutePath());
		
		if(file.canRead())
			System.out.println("Yes,it can read");
		
		if(file.isDirectory())
			System.out.println("Yes,it is a directory");
		else
			System.out.println("No,it is not a directory");
		
		if(file.isAbsolute())
			System.out.println("this is a absolute directory");
		else
			System.out.println("this is not a abasolute directory");
		
		//列出目录下的所有文件
		String[] list = file.list();
		System.out.println("该目录下的文件有：");
		for(String filename : list){
			System.out.println(filename);
		}
		
		//另外一种实现方式，高级一些
		File[] files = file.listFiles();
		System.out.println("该目录下的目录有：");
		for(File f : files)
			if(f.isDirectory())
				System.out.println(f);
		
		System.out.println("该目录下的文件有：");
		for(File f : files)
			if(f.isFile())
				System.out.println(f);
	}
}
