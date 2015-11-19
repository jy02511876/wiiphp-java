package kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerTest {
	public static void main(String[] args) {
		int times = Integer.parseInt(args[0]);
		Properties props = new Properties();
		props.put("metadata.broker.list","master:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		//props.put("partitioner.class", "kafka.SimplePartitioner");
		props.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String,String> producer = new Producer<String,String>(config);
		
		Random rnd = new Random();
		for (long nEvents = 0; nEvents < times; nEvents++) { 
			long runtime = new Date().getTime();
			String ip = "192.168.2."+rnd.nextInt(255);
			String msg = runtime+",www.example.com,"+ip;
			
			KeyedMessage<String,String> data = new KeyedMessage<String,String>("test",ip,msg);
			producer.send(data);
		}
		producer.close();
	}
}
