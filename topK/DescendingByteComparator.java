package topK;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Sorts ByteWritables in descending order
 * @author hduser
 *
 */
public class DescendingByteComparator extends WritableComparator {
	
	protected DescendingByteComparator() {
		super(ByteWritable.class, true);
	}
		 
	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {
		ByteWritable k1 = (ByteWritable)w1;
		ByteWritable k2 = (ByteWritable)w2;
		return -1 * k1.compareTo(k2);
	}    

}
