/*
 * Assignment 3
 * Read an input of text file as stream
 * Use Regular Expression pattern to filter out hex number, using Stream and Parallel Stream.
 * Double the stream size and Compare the time required for Stream and Parallel Stream 
 * until Parallel Stream is faster than Stream.
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lesson3Stream {
	
	public static void main(String[] args) throws IOException{
	try{
		String content=new String(Files.readAllBytes(Paths.get(args[0])),StandardCharsets.UTF_8);
		System.out.printf("String Size: %d \n",content.length());
		List<String>lines=Arrays.asList(content.split("\\W"));
		
		long count=0;	//occurrences of hex 
		long timeSP=0; //time (millisecs) for stream 
        long timeS=0; //time (millisecs) for parallelStream
        Predicate<String> pattern =Pattern
				.compile("\\d+(?:[a-z][a-z]*[0-9]+[a-z0-9]*)|0{7,9}\\d+", Pattern.CASE_INSENSITIVE|Pattern.DOTALL)
				.asPredicate(); //regular expression pattern
        
        /*While Loop. Compute the time required for stream and parallel stream
        *  until streamParallel() method is faster then Stream()
		*/
        while(timeSP==timeS|timeSP>=timeS){
        	//use Stream()
        	long startS = System.currentTimeMillis(); 
        	System.out.println("Stream");
        	count=useStream(lines,pattern);
        	System.out.println("	There are "+count+ " occurrences");
        	long endS = System.currentTimeMillis();	
        	timeS=endS-startS;
        	System.out.println("	Millisecs using stream: "+timeS);
        	//use parallelStream
        	long startSP = System.currentTimeMillis();
        	System.out.println("Parallel Stream");
        	count=useParallelStream(lines,pattern);
        	System.out.println("	There are "+count+ " occurrences");
        	long endSP = System.currentTimeMillis();
        	timeSP=endSP-startSP;
        	System.out.println("	Millisecs using parallelStream: "+timeSP);
        	if (timeSP>=timeS){
        		System.out.println("Result Stream was "+(timeSP-timeS)+" millisecs faster than Parallel Stream");
        		System.out.println("Doubleing the stream size and try again");
        		content=content+content;
            	System.out.printf("String Size: %d \n",content.length());
            	lines=Arrays.asList(content.split("\\W"));
            	}
        	}

	}catch (IOException e){
		System.out.println("The Error is: "+e);
	}
	}
	/*
	 * Use Stream() to filter hex number, using regular expression pattern 
	 * Parameters are string list from the text file and regular expression pattern
	 * Return the counting of stream
	 */
	public static Long useStream(List<String> lines, Predicate<String> pattern){
    	long occurrence=lines
    					.stream()
    					.filter(pattern)
    					.count();
		List<String>filterOutS=lines
				.stream()
				.filter(pattern)
				.collect(Collectors.<String>toList());
    	//iterate through the string list and print out each element 
    	for (int i = 0; i < filterOutS.size(); i++) {
			System.out.println("		"+filterOutS.get(i));
		}
    	return occurrence;
    	//return filterOutS;
    }
	/*
	 * Use parrallelStream() to filter hex number, using regular expression pattern 
	 * Parameters are string list from the text file and regular expression pattern
	 * Return the counting of parallel stream
	 */
	public static Long useParallelStream(List<String> lines, Predicate<String> pattern){
		long occurrence= lines
						.parallelStream()
						.filter(pattern)
						.count();
		List<String>filterOutSP=lines
				.parallelStream()
				.filter(pattern)
				.collect(Collectors.<String>toList());
		//iterate through the string list and print out each element
		for (int i = 0; i < filterOutSP.size(); i++) {
			System.out.println("		"+filterOutSP.get(i));
		}
    	return occurrence;
		//return filterOutSP;
    }
    
}

