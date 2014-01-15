package sort;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class Chinese {
	public static void main(String[] args){
		String[] strs = {"张三(Z)","李四(L)","王五(W)"};
		Arrays.sort(strs);
		System.out.println("用Arrays的sort对中文排序不正确：");
		for(String str : strs)
			System.out.println("\t"+str);
		
		System.out.println("通过Collator方法对中文排序：");
		Comparator c = Collator.getInstance(Locale.CHINA);
		Arrays.sort(strs,c);
		for(String str : strs)
			System.out.println("\t"+str);
		
		System.out.println("通过Collator方法对中文排序,部分疑难字还是不正确：");
		String[] words = {"犇(B)","鑫(X)"};
		Arrays.sort(words,c);
		for(String word : words)
			System.out.println("\t"+word);
	}
}
