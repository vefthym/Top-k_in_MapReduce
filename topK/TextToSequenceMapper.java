package topK;

import java.io.IOException;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;



public class TextToSequenceMapper extends MapReduceBase implements Mapper<LongWritable, Text, VIntWritable, ByteWritable> {
	
	VIntWritable objectId = new VIntWritable();	
	ByteWritable score = new ByteWritable();
	/**
	 * input: object score
	 * output the same with object as VIntWritable and score as ByteWritable
	 */
	public void map(LongWritable key, Text value,
			OutputCollector<VIntWritable, ByteWritable> output, Reporter reporter) throws IOException {
		
	
		String[] block = value.toString().split(" ");
		objectId.set(Integer.parseInt(block[0]));
		score.set(Byte.parseByte(block[1]));

		output.collect(objectId, score);
	}
	
}
