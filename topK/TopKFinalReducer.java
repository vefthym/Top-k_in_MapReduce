package topK;


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TopKFinalReducer extends MapReduceBase implements Reducer<ByteWritable, VIntWritable, VIntWritable, ByteWritable> {
	
	double minValue;
	int extraElements;
	public void configure(JobConf conf) {
		minValue = Double.parseDouble(conf.get("min", "0"));
		extraElements = conf.getInt("extra", 0);
	}

	public void reduce(ByteWritable key, Iterator<VIntWritable> values,
			OutputCollector<VIntWritable, ByteWritable> output, Reporter reporter) throws IOException {			
			
			if (key.get() == minValue) { //edge in topK+extraElements => skip ExtraElements
				int counter = 0;
				while (values.hasNext()) { //skip extraElements
					values.next();
					if (++counter == extraElements) {
						break;
					}
				}
				
			} 
			
			//output the rest of the edges
			while (values.hasNext()) {
				output.collect(values.next(), key);
			}
			
			
	}

}
