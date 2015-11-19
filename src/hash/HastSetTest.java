package hash;

import java.util.HashSet;

public class HastSetTest {
	public static void main(String[] args){
		HashSet<Integer> uids = new HashSet<Integer>();
		
		uids.add(123);
		uids.add(1231);
		System.out.println(uids.size());
	}
}
