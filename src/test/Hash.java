package test;

public class Hash {

	private static int incr = 0;
	private final static int sys = 001;
	
	public void getTid(int apptype)
	{
		
	}
	public static void main(String[] args) {
		String uid = "zhoubin";
		int hashCode = uid.hashCode();
		System.out.println(hashCode);
		int p = hashCode % 256;
		System.out.println(p);
	}
}
