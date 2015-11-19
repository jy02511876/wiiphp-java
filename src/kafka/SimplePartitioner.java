package kafka;

import kafka.producer.Partitioner;

public class SimplePartitioner implements Partitioner {

	@Override
	public int partition(Object key, int a_numPartitioner) {
		int partition = 0;
		String stringKey = (String) key;
		int offset = stringKey.lastIndexOf('.');
		if(offset > 0){
			partition = Integer.parseInt(stringKey.substring(offset+1)) % a_numPartitioner;
		}
		return 0;
	}
	
}
