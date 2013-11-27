package file;

import java.io.IOException;

public class CopyTest {
	public static void main(String[] args){
		String fromName = "copy_test.txt";
		String toName = "copy_test.txt.bak";
		try {
			FileIO.copyFile(fromName,toName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String content = FileIO.renderToString(fromName);
			System.out.println(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finish");
	}
}
