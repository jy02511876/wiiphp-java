package hadoop;

import java.io.IOException;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import s_cn.Goods;

public class SequenceFileTest extends Configured implements Tool {

	private static Logger logger = Logger.getLogger(SequenceFileTest.class);
	
	public static class Map extends Mapper<LongWritable,Text,LongWritable,Text>{

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StrTokenizer tokenizer = new StrTokenizer(line);
			String[] data = tokenizer.getTokenArray();
			if(data.length != 0){
				if(data.length < 5){
					logger.info(line);
				}else{
					StringBuilder titleBuilder = new StringBuilder();
					for(int i=4;i<data.length;i++){
						titleBuilder.append(data[i]);
					}
					String title = titleBuilder.toString();
					double price = Double.parseDouble(data[1]);
					double delPrice = Double.parseDouble(data[2]);
					String url = data[0];
					String imageUrl = data[3];
					Goods goods = new Goods(title,price,delPrice,url,imageUrl);
					context.write(key, new Text(goods.toString()));
				}
				
			}
			
		}
		
	}
	
	public int run(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		Configuration conf = getConf();
		Job job = new Job(conf,"SequenceFileTest");
		job.setJarByClass(SequenceFileTest.class);
		
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setMapperClass(Map.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setCompressOutput(job, true);
		SequenceFileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
		SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 1 : 0;
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new SequenceFileTest(),args);
		System.exit(res);
	}
	
}