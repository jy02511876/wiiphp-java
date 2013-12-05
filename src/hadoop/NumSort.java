package hadoop;

/**
 * 数字排序
 * 输入文件：
 * file1:
 * 	2
 * 	64
 * 	32
 * file2:
 * 	65223
 * 	546
 * file3:
 * 	87
 * 	123
 * 
 * 思路，还是使用MR的特性，key会自动排序功能，将数字放入key中
 * 但是每个map，reduce的数据是局部的，不能进行全局的排序
 * 所以使用partition方法，使分发数据的时候，整体是有序的，然后在局部排序，这样总的就是有序的
 * 
 * 
 */
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class NumSort extends Configured implements Tool {

	private static Logger logger = Logger.getLogger(NumSort.class);
	
	public static class Map extends Mapper<LongWritable,Text,IntWritable,IntWritable>{

		private static IntWritable num = new IntWritable();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			num.set(Integer.parseInt(line));
			context.write(num, new IntWritable(1));
		}
		
	}
	
	public static class Reduce extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable>{

		private static IntWritable linenum = new IntWritable(1);
		@Override
		protected void reduce(IntWritable key, Iterable<IntWritable> values,Context context)
				throws IOException, InterruptedException {
			for(IntWritable val : values){
				context.write(linenum, key);
				linenum = new IntWritable(linenum.get()+1);
			}
		}
	}
	
	
	public static class Partition extends Partitioner<IntWritable,IntWritable>{

		@Override
		public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
			
			int keyNumber = key.get();
			int maxNumber = 65224;
			int halfNumber = maxNumber/2;
			int result = keyNumber <= halfNumber ? 0 : 1;
			
			logger.info("numPartition is:"+numPartitions);
			logger.info("result is:"+result);
			
			return result;
		}
		
	}
	
	
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf,"num sort demo");
		job.setJarByClass(NumSort.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setPartitionerClass(Partition.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		job.setNumReduceTasks(2);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true)?1:0;
	}
	
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(), new NumSort(), args);
		System.exit(res);
	}
	
}
