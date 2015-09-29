package topK;


import java.io.IOException;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class TopKMapper extends MapReduceBase implements Mapper<VIntWritable, ByteWritable, ByteWritable, VIntWritable> {
 
	VIntWritable one = new VIntWritable(1);
	
	/**	 
	 * output for each input edge both its nodes as keys and the other node and weight as value
	 * @param key i,j entity ids
	 * @param value wij the weight of this edge
	 * @param output key:i value:j,wij and the inverse (key:j value:i,wij) 
	 */
	public void map(VIntWritable key, ByteWritable value,
			OutputCollector<ByteWritable, VIntWritable> output, Reporter reporter) throws IOException {		
		output.collect(value, one);
	}

}
