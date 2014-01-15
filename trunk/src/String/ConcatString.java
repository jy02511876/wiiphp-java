package String;

public class ConcatString {
	
	public static final int times = 50000;
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		//addWay();
		//concatWay();
		StringBuilderWay();
		//StringBufferWay();
		System.out.println(System.currentTimeMillis() - startTime);
	}
	
	
	/**
	 * used:1000ms
	 */
	public static void addWay(){
		String str = "";
		for(int i=0; i< times;i++){
			str += "c";
		}
	}
	
	/**
	 * used:500ms
	 */
	public static void concatWay(){
		String str = "";
		for(int i=0;i<times;i++)
			str = str.concat("c");
	}
	
	/**
	 * first time:100ms
	 * after : 14ms
	 */
	public static void StringBuilderWay(){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<times;i++)
			builder.append("c");
		builder.toString();
	}
	
	
	/**
	 * used : 14ms
	 */
	public static void StringBufferWay(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<times;i++)
			buffer.append("c");
		buffer.toString();
	}
}
