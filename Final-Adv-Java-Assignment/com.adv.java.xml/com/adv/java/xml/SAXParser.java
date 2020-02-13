package com.adv.java.xml;

import java.io.FileInputStream;

import com.adv.java.xml.internal.SAXParserImpl;
import com.adv.java.iostream.FileIOStream;

public class SAXParser {
	public static String[] xmlResult;
	public String outputString;
	
	//Take in the file name as a string, call the file reading method that will read in a file
	// and return a FileInputStream representing a xml file
	//call parserSAX method of SAXParserImpl to parse the file
	//return a string representation of the parsing's results
	public String XMLOutput (String filename) {
	  try{
			FileInputStream in = new FileIOStream().input(filename);
			xmlResult= new SAXParserImpl().parserSAX(in);
			outputString =String.format("Results of XML Parsing using SAX Parser: \n"
					+ "	serial: %s \n"
					+ "	visible-string: %s \n"
					+ "	unsigned: %s ",
					xmlResult[0],xmlResult[1],xmlResult[2]);
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputString;
	}
	 
}
