package crawler.s.cn;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.io.RCFileOutputFormat;
import org.apache.hadoop.hive.serde2.columnar.BytesRefArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.log4j.Logger;

public class ShoeJob {
	private Logger logger = Logger.getLogger(ShoeJob.class);
	
	public static int main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		//Path path = new Path();
		Job job = new Job();
		Configuration conf = job.getConfiguration();
		job.setJobName("crawler:s.cn");
		job.setJarByClass(ShoeJob.class);
		
		RCFileOutputFormat.setColumnNumber(conf, 4);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setInputFormatClass(TextInputFormat.class);
		
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setMapperClass(Map.class);
		
		return job.waitForCompletion(true) ? 0 : 1; 
	}
	
	
	
	public static class Map extends Mapper<LongWritable,Text,NullWritable,BytesRefArrayWritable>{
		public void map(LongWritable key,Text value,Context context){
			
			//context.write(null, value);
		}
	}
	
	
}
