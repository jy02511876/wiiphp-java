package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TimeCricle {
	public static void main(String[] args){
		       
		String[] commands = new String[]{"tail -f test.txt"};
		Process process;
		try {
			process = Runtime.getRuntime().exec (commands);
			InputStreamReader ir=new InputStreamReader(process.getInputStream());
			BufferedReader input = new BufferedReader(ir);
			String line;
			while ((line = input.readLine ()) != null){   
				System.out.println(line);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
