import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.FileInputStream;
//import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson2ReEx {
	
	
	public static void main(String[] args){
		List<String>list1=new ArrayList<String>();
		List <String>list2=new ArrayList<String>();
		List<String>list3=new ArrayList<String>();
	System.out.println("Processed the following input file: ");
	System.out.printf("%s"+"\n"+"Results are as follows"+"\n",args[0]);
	try(BufferedReader in =new BufferedReader(
			new InputStreamReader(
					new FileInputStream(args[0]),"UTF8"))){
		
		String input;
		while((input=in.readLine()) != null){
			//Find PAN IDs
			String re1="(?:[a-z][a-z]+)\\s+[=]\\s+\\d+(?:[a-z][a-z0-9_]*)";
		    Pattern p1 = Pattern.compile(re1,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m1 = p1.matcher(input); 
		    while (m1.find())
		    {
		    	String panID=m1.group();
		    	list1.add(panID);
		    }
		    //Find MAC Addresses
		    String re2="[0-9]{5,7}(?:[a-z][a-z0-9_]*)";
		    Pattern p2 = Pattern.compile(re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m2 = p2.matcher(input);
		    while (m2.find()){
		    	String address=m2.group();
		        list2.add(address);
		    }
		    //Find FR_RSSI_M value for each MAC address
		    String re3="(\\d{5,7}(?:[a-z]\\w*)).*?((?:-?\\d*\\.\\d+)(?![-+\\d.]))";
		    Pattern p3 = Pattern.compile(re3,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m3 = p3.matcher(input);
		    while (m3.find())
		    {
		        String all = m3.group(1)+m3.group(2);
		        //to insert a space between MAC address and RF_RSSI_M value
		        String result = all.replaceAll("(\\d{5,7}(?:[a-z]\\w*))((?:-?\\d*\\.\\d+)(?![-+\\d.]))","$1 $2");
		        list3.add(result);
		    }      
		}
		System.out.printf("-List of PAN IDs (Total of %d)"+"\n",list1.size());
		list1.forEach(System.out::println);
		System.out.printf("-List of MAC Addresses (Total of %d)"+"\n",list2.size());
		list2.forEach(System.out::println);
		System.out.printf("-List of RF_RSSI_M values for each MAC address"+"\n");
		list3.forEach(System.out::println);
		
		in.close();
	}catch (Exception e){
		System.err.println("Error: "+e.getMessage());
	}
		
	}
}