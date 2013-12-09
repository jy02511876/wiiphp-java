package crawler.s.cn;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import s_cn.Goods;
import s_cn.crawler.CrawlerByJsoup;


public class GoodsJob {
	private Logger logger = Logger.getLogger(GoodsJob.class);
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		Job job = new Job();
		Configuration conf = job.getConfiguration();
		
		job.setJobName("crawler:s.cn");
		job.setJarByClass(GoodsJob.class);
		job.setMapperClass(Map.class);
		
		//RCFileOutputFormat.setColumnNumber(conf, 5);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//job.setInputFormatClass(TextInputFormat.class);
		//job.setOutputFormatClass(TextFileOutputFormat.class);
		
		
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		
		
		System.exit(job.waitForCompletion(true) ? 0 : 1); 
	}
	
	
	
	public static class Map extends Mapper<LongWritable,Text,NullWritable,Text>{
		boolean init = false;
		public void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
			if(!init)
					initCounter();
			CrawlerByJsoup c = new CrawlerByJsoup();
			List<Goods> goods = c.crawler();
			for(Goods g : goods){
				context.write(NullWritable.get(), new Text(g.toString()));
			}

		}
		
		public synchronized void initCounter() {
            if (init)
                return;
            
            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
                    
                    boolean run = true;
                    
                    while (run) {                        
                        try {
                            Thread.sleep(1000 * 60);
                        } catch (InterruptedException e) {
                            run = false;
                        }
                    }
                }
            };
            t.setDaemon(true);
            t.start();
            
            init = true;
        }
		
	}
	
	
}
