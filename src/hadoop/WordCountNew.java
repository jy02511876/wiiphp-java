package hadoop;

/**
 * 原始文件如下：
 * file1:
 * 	hello world
 * file2:
 * 	hello hadoop
 * 	hello mapreduce
 * 
 * Map Task1:
 * 	<"hello",1>
 * 	<"world",1>
 * Map Task2:
 * 	<"hello",1>
 * 	<"hadoop",1>
 * 	<"hello",1>
 * 	<"mapreduce",1>
 * 
 * Combiner:（先将局部用Reduce合并一下，减少网络压力）
 * Map Task1:
 * 	<"hello",1>
 * 	<"world",1>
 * Map Task2:
 * 	<"hadoop",1>
 * 	<"hello",2>
 * 	<"mapreduce",1>
 * 
 * 然后是MapReduce的shuffle过程（系统自身的一个过程）:
 * <"hadoop",1>
 * <"hello",<1,2>>
 * <"mapreduce",1>
 * <"world",1>
 * 
 * 最终通过reduce后的结果:
 * <"hadoop",1>
 * <"hello",3>
 * <"mapreduce",1>
 * <"world",1>
 * 
 */
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountNew extends Configured implements Tool {
	
	public static class Map extends Mapper<LongWritable,Text,Text,IntWritable>{
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while(tokenizer.hasMoreTokens()){
				word.set(tokenizer.nextToken());
				context.write(word, one);
			}
		}
	}

	
	public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable>{

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable val : values){
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
		
	}
	
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf,"word count new");
		job.setJarByClass(WordCountNew.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 1 : 0;
	}
	
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(), new WordCountNew(), args);
		System.exit(res);
	}
}
