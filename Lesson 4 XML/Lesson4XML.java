import java.io.FileInputStream;
import java.io.FileReader;

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
 * Assignment 4
 * A console application that reads the file (JobResults_UCSD.xml)
 * Uses XML parsings (DOM, SAX and Xpath) to return node values
 */

public class Lesson4XML {

	//fields for storing output of DOM parsing
	private static String serial_DOM; 
	private static String visibleString_DOM;
	private static int unsigned_DOM;
	
	//fields for storing output of SAX parsing
	private static String serial_SAX;
	private static String visibleString_SAX;
	private static int unsigned_SAX;
	
	//fields for storing output of XPath parsing
	private static String serial_Xpath;
	private static String visibleString_Xpath;
	private static int unsigned_Xpath;
	
	//fields for storing output of StAX parsing
	private static String serial_StAX;
	private static String visibleString_StAX;
	private static int unsigned_StAX;

	public static void main(String[] args){
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			FileInputStream in = new FileInputStream(args[0]);
			Document doc = builder.parse(in);
			parserDOM(doc);
			System.out.printf("Results of XML Parsing using DOM Parser: \n"
					+"	serial: %s \n"
					+"	visible-string: %s \n"
					+"	unsigned: %d \n",
					serial_DOM,visibleString_DOM,unsigned_DOM);
			
			parserSAX(args[0]);
			System.out.printf("Results of XML Parsing using SAX Parser: \n"
					+"	serial: %s \n"
					+"	visible-string: %s \n"
					+"	unsigned: %d \n",
					serial_SAX,visibleString_SAX,unsigned_SAX);
			
			parserXpath(doc);
			System.out.printf("Results of XML Parsing using XPath: \n"
					+"	serial: %s \n"
					+"	visible-string: %s \n"
					+"	unsigned: %d \n",
					serial_Xpath,visibleString_Xpath,unsigned_Xpath);
			
			parserStAX(args[0]);
			System.out.printf("Results of XML Parsing using StAX Parser: \n"
					+"	serial: %s \n"
					+"	visible-string: %s \n"
					+"	unsigned: %d \n",
					serial_StAX,visibleString_StAX,unsigned_StAX);
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * DOM parsing method that takes in one parameter
	 *  doc Document 
	 */
	private static void parserDOM(Document doc) {
		//create element and node list out of document
		Element root = doc.getDocumentElement();
		NodeList children = root.getChildNodes();
		//outer for loop for finds "serial" value, looping through children node list
		for(int i=0; i<children.getLength(); i++){
			NodeList childList=children.item(i).getChildNodes();
			Node childrenNode=children.item(i);
			if(childrenNode.getNodeName().equals("serial"))
				serial_DOM=childrenNode.getTextContent();
			//first inner for loop finds "visible-string" value, looping through child node list
			for(int j=0;j<childList.getLength();j++){
				NodeList gchildList=childList.item(j).getChildNodes();
				Node childNode=childList.item(j);
				if(childNode.getNodeName().equals("visible-string"))
					visibleString_DOM=childList.item(j).getTextContent().trim();
				//second inner for loop finds "unsigned" value, looping through gchild node list
				for(int h=0;h<gchildList.getLength();h++){
					Node gchildNode=gchildList.item(h);
					if(gchildNode.getNodeName().equals("unsigned"))
						unsigned_DOM=Integer.parseInt(gchildList.item(h).getTextContent().trim());
					}
				}
		}
	}
	/*
	 * SAX parsing method that takes in one parameter
	 * String filename
	 */
	private static void parserSAX(String filename){
		//obtain and configure a SAX based parser
		SAXParserFactory factorySAX=SAXParserFactory.newInstance();
		try{
			FileInputStream in = new FileInputStream(filename);
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
	}
	/*
	 * XPath parsing method that takes in one parameter
	 * doc Document
	 */
	private static void parserXpath(Document doc) {
		try{
		XPath xpath=XPathFactory.newInstance().newXPath();
		serial_Xpath = xpath.evaluate("/jobresult/serial",doc);
		visibleString_Xpath=xpath.evaluate("/jobresult/data/visible-string", doc);
		unsigned_Xpath=Integer.parseInt(xpath.evaluate("/jobresult/data/structure/unsigned", doc));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * StAX parsing method that takes in one parameter
	 * String filename
	 */
	private static void parserStAX(String filename){
		boolean serial=false;
		boolean visibleString=false;
		boolean unsigned=false;
		XMLInputFactory factory=XMLInputFactory.newInstance();
		try{
			XMLEventReader eventReader=factory.createXMLEventReader(new FileReader(filename));
			while(eventReader.hasNext()){
				XMLEvent event=eventReader.nextEvent();
				switch(event.getEventType()){
				case XMLStreamConstants.START_ELEMENT:
					StartElement start=event.asStartElement();
					String qname=start.getName().getLocalPart();
					if(qname.equalsIgnoreCase("serial")){
						serial=true;
					}else if(qname.equalsIgnoreCase("visible-string")){
						visibleString=true;
					}else if(qname.equalsIgnoreCase("unsigned")){
						unsigned=true;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					Characters characters=event.asCharacters();
					if(serial){
						serial_StAX=characters.getData();
						serial=false;
					}else if(visibleString){
						visibleString_StAX=characters.getData();
						visibleString=false;
					}else if(unsigned){
						unsigned_StAX=Integer.parseInt(characters.getData());
						unsigned=false;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

