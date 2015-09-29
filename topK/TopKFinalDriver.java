package topK;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;


public class TopKFinalDriver {

	public static void main(String[] args) {
		JobClient client = new JobClient();
		JobConf conf = new JobConf(topK.TopKFinalDriver.class);
		
		conf.setJobName("TopK Final");		
		
		conf.setOutputKeyClass(VIntWritable.class);
		conf.setOutputValueClass(ByteWritable.class);
		
		conf.setInputFormat(SequenceFileInputFormat.class);
		conf.setOutputFormat(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setOutputCompressionType(conf,	CompressionType.BLOCK);

		FileInputFormat.setInputPaths(conf, new Path(args[0])); //list of object score
		FileOutputFormat.setOutputPath(conf, new Path(args[2])); //topK items of the input list
		
		try{
			Path pt=new Path(args[1]+"/part-00000"); //TopKCounting
            FileSystem fs = FileSystem.get(new Configuration());
            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String minValue = br.readLine();
            Integer extraElements = (Integer.parseInt(br.readLine()));
            br.close();
            conf.set("min", minValue);
            conf.setInt("extra", extraElements); 
            System.out.println("min="+minValue);
            System.out.println("extra="+extraElements);
            
            if (extraElements > 0) { //use a reducer  to skip the extra elements         	
            	
            	conf.setMapperClass(topK.TopKFinalMapper.class);
            	conf.setReducerClass(topK.TopKFinalReducer.class);
    		
            	conf.setNumReduceTasks(56);
            	
            	conf.setMapOutputKeyClass(ByteWritable.class);
            	conf.setMapOutputValueClass(VIntWritable.class);
            } else { //don't use a reducer
            	conf.setMapperClass(topK.TopKFinalMapperOnly.class);    		
            	conf.setNumReduceTasks(0);
            }
            
            
	    } catch(Exception e){
	    	System.err.println(e.toString());
	    }

		client.setConf(conf);		
		try {
			JobClient.runJob(conf);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	

}
