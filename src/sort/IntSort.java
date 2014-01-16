package sort;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class IntSort {
	
	public static void main(String[] args){
		Integer[] nums = {5,7,4,2,9,9,8,3,1,2,1,6};
		System.out.println("nums:");
		for(Integer num : nums)
			System.out.print(num.intValue()+" ");
		
		System.out.println("");
		
		
		int max1 = getMaxByFor(nums);
		System.out.println("max num by for is:"+max1);
		
		int max2 = getMaxBySort(nums);
		System.out.println("max num by sort is:"+max2);
		
		int second = getSecondMax(nums);
		System.out.println("second max is:"+ second);
	}
	
	
	/**
	 * 通过自定义的for循环，循环一边即可找到最大值
	 * @param nums
	 * @return
	 */
	public static int getMaxByFor(Integer[] nums){
		int max = nums[0];
		for(Integer num : nums){
			if(num.intValue() > max)
				max = num.intValue();
		}
		return max;
	}
	
	
	/**
	 * 通过数组的排序找到最大值，数据量在1W左右，性能差不多
	 * @param nums
	 * @return
	 */
	public static int getMaxBySort(Integer[] nums){
		int[] numbers = new int[nums.length];
		for(int i=0;i<nums.length;i++)
			numbers[i] = nums[i].intValue();
		
		Arrays.sort(numbers);
		return numbers[numbers.length-1];
	}
	
	
	/**
	 * 取老二，思路：先去重，在排序，在取第2个值
	 * @param nums
	 * @return
	 */
	public static int getSecondMax(Integer[] nums){
		List<Integer> dataList = Arrays.asList(nums);
		//转换为treeset，自动取重，并且升序排序
		TreeSet<Integer> ts = new TreeSet<Integer>(dataList);
		return ts.lower(ts.last());
	}
}
