package base.type;

public class BinaryTest {
	public static void main(String[] args){
		int num = 2;
		String str = "a";
		
		System.out.println(Integer.toBinaryString(num));
		int strNum = Integer.parseInt(str,36);
		System.out.println(strNum);
		System.out.println(Integer.toBinaryString(strNum));
	}
}

