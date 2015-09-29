package topK;


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TopKCombiner extends MapReduceBase implements Reducer<ByteWritable, VIntWritable, ByteWritable, VIntWritable> {

	VIntWritable toEmit = new VIntWritable();
	
	//get keys (weights) in descending order
	public void reduce(ByteWritable key, Iterator<VIntWritable> values,
			OutputCollector<ByteWritable, VIntWritable> output, Reporter reporter) throws IOException {
			
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			toEmit.set(sum);
			output.collect(key, toEmit);
	}

}
