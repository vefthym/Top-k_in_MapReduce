package topK;


import java.io.IOException;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class TopKFinalMapperOnly extends MapReduceBase implements Mapper<VIntWritable, ByteWritable, VIntWritable, ByteWritable> {
 
	double minValue;	
	
	public void configure(JobConf conf) {
		minValue = Double.parseDouble(conf.get("min", "0"));		
	}
	
	/**
	 * emit only edges that have value >= minValue (i.e. belong in top k edges)
	 */
	public void map(VIntWritable key, ByteWritable value,
			OutputCollector<VIntWritable, ByteWritable> output, Reporter reporter) throws IOException {
		double weight = value.get();
		
		if (weight >= minValue) { //edge belongs in top k
			output.collect(key, value);
		}
	}

}
