package logs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;


public class Access2Hive extends Configured implements Tool {
	
	private static Logger logger = Logger.getLogger(Access2Hive.class);

	public static class Map extends Mapper<LongWritable,Text,NullWritable,Text>{
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
			String line = value.toString();

			StrTokenizer tokenizer = new StrTokenizer(line);
			StringBuffer userAgent = new StringBuffer();
			
			int index;
			String token;
			while(tokenizer.hasNext()){
				index = tokenizer.nextIndex();
				token = tokenizer.nextToken();
				if(index > 10){
					userAgent.append(token);
					userAgent.append(" ");
				}
			}
			
			List<?> list = tokenizer.getTokenList();
			if(list.size() > 10){
				ArrayList<String> col = new ArrayList<String>(10);
				col.add(0,list.get(0).toString());					//remote_addr
				col.add(1,"");										//http_x_forwarded_for
				col.add(2,list.get(5).toString().substring(1));		//header
				col.add(3,list.get(6).toString());					//request_url
				col.add(4,StringUtils.removeEnd(list.get(7).toString(),"\""));		//http
				col.add(5,list.get(8).toString());					//status
				col.add(6,list.get(9).toString());					//body_bytes_sent
				
				col.add(7,StringUtils.remove(StringUtils.remove(list.get(10).toString(), "\""),"-"));					//request_body
				col.add(8,"");										//http_referer
				col.add(9,StringUtils.remove(StringUtils.remove(StringUtils.trim(userAgent.toString()), "\""),"-"));	//user_agent
				col.add(10,parseDate(list.get(3).toString()));					//time_local
				
				StringBuffer content = new StringBuffer();
				for(int i=0;i<col.size();i++)
					content.append(col.get(i)+",");
				
				String txt = StringUtils.removeEnd(content.toString(), ",");
				context.write(NullWritable.get(), new Text(txt));
			}
		}
	}

	
	private static String parseDate(String fromDate){
		String toDate = "";
		SimpleDateFormat format = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss",Locale.ENGLISH);
		Date d;
		try {
			d = format.parse(fromDate);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			toDate = format1.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return toDate;
	}
	
	
	/*
	public static class Reduce extends Reducer<LongWritable,Text,NullWritable,Text>{

		protected void reduce(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
			context.write(NullWritable.get(), value);
		}
		
	}
	*/
	
	
	

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = new Job(conf,this.getClass().getName());
		job.setJarByClass(Access2Hive.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(Map.class);
		//job.setCombinerClass(Reduce.class);
		//job.setReducerClass(Reduce.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		return job.waitForCompletion(true) ? 1 : 0;
	}
	
	
	public static void main(String[] args) throws Exception{
		int res = ToolRunner.run(new Configuration(), new Access2Hive(), args);
		System.exit(res);
	}

}
