package com.adv.java.iostream.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
/*
 * Contains methods that read in input file and write out output file
 */

public class FileIOStreamImpl {
	//Take the string file name and write it out as a xml file
	//return the FileInputStream of that xml file
	public static FileInputStream FileInput (String filename) throws IOException {
		Reader in=new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
		PrintWriter out=new PrintWriter(new OutputStreamWriter(new FileOutputStream("JobResult.xml"),"UTF-8"),true);
		int i;	
		while((i=in.read())!=-1){
			out.write(i);
		}
		in.close();
		out.close();
		FileInputStream fin = new FileInputStream("JobResult.xml");
		return fin;
	}
	//Take the string file name and read the file as a list of string
	//split the string into string array and return that array
	public static String[] FileReadIn (String filename) throws IOException {
		List<String> lines=Files.readAllLines(new File(filename).toPath()); //filename is dat file
		String []results = null;
		for (String line:lines) {
			results=line.split("(?=<)");
		}
		return results;
	}
}
