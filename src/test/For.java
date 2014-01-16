package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

/**
 * 不同的数据，适用不同的for取循环，以提高性能,性能差距很大
 * @author zhoubin
 *
 */
public class For {
	public static void main(String[] args){
		int studentNumbers = 8*10000;
		List<Integer> scores = new ArrayList<Integer>();
		List<Integer> data = new LinkedList<Integer>();
		//创建数据
		for(int i=0;i<studentNumbers;i++){
			int n = new Random().nextInt(150);
			scores.add(n);
			data.add(n);
		}
		
		System.out.println("ArrayList data:");
		long startTime1 = System.currentTimeMillis();
		int avgscore1 = avgscore1(scores);
		System.out.println("avg_score:"+avgscore1);
		System.out.println("used:"+(System.currentTimeMillis() - startTime1));
		
		
		long startTime2 = System.currentTimeMillis();
		int avgscore2 = avgscore2(scores);
		System.out.println("avg_score:"+avgscore2);
		System.out.println("used:"+(System.currentTimeMillis() - startTime2));
		
		System.out.println("LinkedList data:");
		long startTime3 = System.currentTimeMillis();
		int avgscore3 = avgscore1(data);
		System.out.println("avg_score:"+avgscore3);
		System.out.println("used:"+(System.currentTimeMillis() - startTime3));
		
		long startTime4 = System.currentTimeMillis();
		int avgscore4 = avgscore2(data);
		System.out.println("avg_score:"+avgscore4);
		System.out.println("used:"+(System.currentTimeMillis() - startTime4));
		
		
		System.out.println("fastest for way:");
	
		long startTime5 = System.currentTimeMillis();
		int avgscore5 = fastAvgScore(scores);
		System.out.println("avg_score:"+avgscore5);
		System.out.println("used:"+(System.currentTimeMillis() - startTime5));
		
		long startTime6 = System.currentTimeMillis();
		int avgscore6 = fastAvgScore(data);
		System.out.println("avg_score:"+avgscore6);
		System.out.println("used:"+(System.currentTimeMillis() - startTime6));
		
	}
	
	
	public static int avgscore1(List<Integer> scores){
		int sum = 0;
		for(int score : scores)
			sum += score;
		return sum/scores.size();
	}
	
	
	public static int avgscore2(List<Integer> scores){
		int sum = 0;
		for(int i=0,size = scores.size();i<size;i++)
			sum += scores.get(i);
		
		return sum/scores.size();
	}
	
	
	public static int fastAvgScore(List<Integer> list){
		int sum = 0;
		if(list instanceof RandomAccess){
			for(int i=0,size = list.size();i<size;i++)
				sum += list.get(i);
		}else{
			for(int score : list)
				sum += score;
		}
		return sum/list.size();
	}
	
	
}
