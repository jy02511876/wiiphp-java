package hadoop;

/**
 * 去重
 * 输入文件
 * file1:
 * 	2006-6-10 a
 * 	2006-6-11 b
 * 	2006-6-12 a
 * 	...
 * file2:
 * 	2006-6-10 a
 * 	2006-6-11 c
 * 	2006-6-12 b
 * 	...
 * 思路：
 * 将一行记录，作为key，MR的特性，自然就把相同的去掉了
 * 
 */


import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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

public class WordSet extends Configured implements Tool {
	
	public static class Map extends Mapper<LongWritable,Text,Text,Text>{
		private static Text line = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			line = value;
			context.write(line, new Text(""));
		}
	}
	
	
	public static class Reduce extends Reducer<Text,Text,Text,Text>{

		@Override
		protected void reduce(Text key, Iterable<Text> value,Context context)
				throws IOException, InterruptedException {
			context.write(key, new Text(""));
		}
		
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf,"word set demo");
		job.setJarByClass(WordSet.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true)? 1:0;
	}

	
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(), new WordSet(), args);
		System.exit(res);
	}
}
