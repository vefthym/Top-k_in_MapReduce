package topK;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.TextInputFormat;


public class TopKCountingDriver {

	public static void main(String[] args) {
		JobClient client = new JobClient();
		JobConf conf = new JobConf(topK.TopKCountingDriver.class);
		
		conf.setJobName("topK Counting (1rst job)");
		
		conf.setMapOutputKeyClass(ByteWritable.class); //a byte is enough, since keys are in [0,100]
		conf.setMapOutputValueClass(VIntWritable.class);
		
		conf.setOutputKeyClass(VIntWritable.class);
		conf.setOutputValueClass(NullWritable.class);
		
		conf.setOutputKeyComparatorClass(topK.DescendingByteComparator.class); //sort bytes in descending order
		
		conf.setInputFormat(SequenceFileInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		conf.setInt("K", Integer.parseInt(args[0]));

		FileInputFormat.setInputPaths(conf, new Path(args[1])); //a list of (object, score) pairs
		FileOutputFormat.setOutputPath(conf, new Path(args[2])); //minValue and extra (more than k) elements

		conf.setMapperClass(topK.TopKMapper.class); 
		conf.setCombinerClass(topK.TopKCombiner.class);
		conf.setReducerClass(topK.TopKReducer.class); 
		
		//conf.set("mapred.reduce.slowstart.completed.maps", "1.00");
		conf.setNumReduceTasks(1);
		
		conf.setCompressMapOutput(true);
                
         
		client.setConf(conf);		
		try {
			JobClient.runJob(conf);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	

}
