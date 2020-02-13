package com.adv.java.xml.internal;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
/*
 * Uses XML parsingsSAX to return node values
 */
public class SAXParserImpl {

	//fields for storing output of SAX parsing
	public static String serial_SAX;
	public static String visibleString_SAX;
	public static int unsigned_SAX;
	public static String[] xmlResult;
	public static String[] outputStrings;
	
	/*
	 * SAX parsing method that takes in one parameter
	 * String filename
	 */
	public static String[] parserSAX(FileInputStream in){
		//obtain and configure a SAX based parser
		SAXParserFactory factorySAX=SAXParserFactory.newInstance();
		try{
			//FileInputStream in = new FileInputStream(filename);
			//obtain object for SAX parser
			SAXParser parser=factorySAX.newSAXParser();
			/*
			 * default handler for SAX handler class
			 */
			DefaultHandler handler =  new DefaultHandler(){
			boolean serial=false;
			boolean visibleString=false;
			boolean unsigned=false;
			//call this method when ever the parser gets an open tag '<'
			 //identifies the tag and assigns an open flag
			public void startElement(String uri, String localName, String qName, Attributes attributes){
				if(qName.equalsIgnoreCase("serial")){
					serial=true;
				}
				else if(qName.equalsIgnoreCase("visible-string")){
					visibleString=true;
				}
				else if(qName.equalsIgnoreCase("unsigned")){
					unsigned=true;
				}
			}
			//this method is called when ever the parser gets a closing tag '>'
			public void endElement(String uri, String localName, String qName){
				
			}
			//store data in between open and closing tags '<' and '>'
			public void characters(char ch[], int start, int length){
				if (serial){
					serial_SAX=new String(ch, start, length);
					serial=false;}
				else if(visibleString){
					visibleString_SAX=new String(ch,start,length);
					visibleString=false;}
				else if(unsigned){
					unsigned_SAX=Integer.parseInt(new String(ch,start,length));
					unsigned=false;}
			}
		};
		parser.parse(in,handler);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		String[] xmlResult = new String[] {serial_SAX, visibleString_SAX, Integer.toString(unsigned_SAX)};
		return xmlResult;
	}
}

