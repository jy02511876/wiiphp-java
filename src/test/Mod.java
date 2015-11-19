package test;

public class Mod {
	public static void main(String[] args){
		
		for(int i=0;i<10000;i++){
			if(i % 500 == 0){
				System.out.println(i);
			}
		}
	}
}
