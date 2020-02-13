package com.adv.java.application;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.adv.java.concurrency.ConcurrentThreadCount;
import com.adv.java.iostream.FileIOStream;
import com.adv.java.regex.RegexFilter;
import com.adv.java.xml.SAXParser;
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		//use iostream to read in JobResult.dat and split and print out the content
		System.out.println("First: use module	com.adv.java.iostream	to input the file JobResult.dat and convert it to xml file. "
				+"\nThe contents of JobResult.dat file are the following:");
		String [] inputLines=new FileIOStream().inputContent("JobResult.dat");
		for (String line:inputLines) {
			System.out.println("     "+line);
		}
		//use regex to filter out the distinctive tags found in the .dat file (which has now been converted to xml file)
		System.out.println("\nSecond: use module	com.adv.java.regex	to find out how many distintive tags are there in the xml file. " );
		List<String> rexOutput = new RegexFilter().RexOutput(inputLines);
		System.out.printf("There are %d distintive tags \n", rexOutput.size());
		for(int i=0; i<rexOutput.size();i++) {
			System.out.println("	"+rexOutput.get(i));
		}
		//use xml to do SAX parsing and find out the values of serial, visible-string and unsigned tag
		System.out.println("\nThird: use module	com.adv.java.xml	to do XML parsing using SAX Parser to find the following:");
		String resultString=new SAXParser().XMLOutput("JobResult.dat");
		System.out.println(resultString);
		System.out.println("	***The result of SAX Parser is stored as String resultString \n"+
							"\nFourth: use module	com.adv.java.concurrency	to count the number of characters it has in resultString over 1,000 threads, \n"
							+"using three different synchronizations: ");
		//use concurrency to count the characters in the result of SAX parsing over 1,000 threads
		String outputWithoutLock = new ConcurrentThreadCount().withoutLockResult(1000,resultString);
		System.out.println("	WithoutLock Count: "+outputWithoutLock);
		String outputReentrant = new ConcurrentThreadCount().reentrant(1000,resultString);
		System.out.println("	ReentrantLock Count: "+outputReentrant);
		String outputAtomicLong = new ConcurrentThreadCount().atomicLong(1000,resultString);
		System.out.println("	AtomicLong Count: "+outputAtomicLong);
	}

}
