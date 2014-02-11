package redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class RedisOp {
	private Jedis jedis = null;
	
	public RedisOp(){
		jedis = new Jedis("cmaster");
	}
	
	public void run(){
		//SortedSet有序集合
		//Map<String,Double> mems = new Map<String,Double>();
		jedis.zadd("sorted_set",10,"google");
		jedis.zadd("sorted_set",8,"bing");
		jedis.zadd("sorted_set",9,"baidu");
		Set<Tuple> sortedSet = jedis.zrangeWithScores("sorted_set", 0, 10);
		Iterator<Tuple> t = sortedSet.iterator();
		Tuple tuple = null;
		while(t.hasNext()){
			tuple = t.next();
			System.out.println(tuple.getElement()+":"+tuple.getScore());
		}
		System.exit(0);
		
		//set集合
		jedis.sadd("members", "tom","jerry");
		System.out.println(jedis.smembers("members"));
		System.exit(0);
		
		
		//list表
		jedis.del("color");
		jedis.rpush("color", "red");
		jedis.rpush("color", "blue");
		jedis.rpush("color", "green");
		System.out.println(jedis.lindex("color", 2));
		System.exit(0);
		
		//hash表
		jedis.hset("human", "name", "aaa");
		jedis.hset("human", "sex", "male");
		System.out.println(jedis.hget("human", "name"));
		System.out.println(jedis.hget("human", "sex"));
		System.exit(0);
		
		
		//取name1*的所有key
		Set<String> name = jedis.keys("name1*");
		Iterator<String> it = name.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		//取name999的value
		System.out.println(jedis.get("name999"));
		//取随机key
		String randomKey = jedis.randomKey();
		System.out.println(randomKey+":"+jedis.get(randomKey));
		
		//一次取多个
		List<String> values = jedis.mget("name","name1","name10");
		for(int j=0;j<values.size();j++)
			System.out.println(values.get(j));
		
		
	}
	
	
	public void insertData1(){
		for(int i=0;i<1000;i++)
			jedis.set("name"+i,String.valueOf(i));
	}
	
	public long getDbSize(){
		return jedis.dbSize();
	}
	
	public Jedis getJedis(){
		return jedis;
	}

	public static void main(String[] args){
		RedisOp op = new RedisOp();
		op.run();
	}
	
	
	
	
	
}
