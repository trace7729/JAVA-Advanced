package com.adv.java.regex.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexFilterImpl {
	//Take the string array of results and find the string value between < and >, excluding </
	//Filter out the duplicates and return as a string list
	public List<String> RexFilterResults (String[] results) throws IOException{
		List<String>list1=new ArrayList<String>();
		String result1="";
		for(String result:results) {
			String re="(<[^/><]+>)";
			Pattern p = Pattern.compile(re,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		    Matcher m = p.matcher(result);
		    if (m.find())
		    {
		    	result1 = m.group();
		    	list1.add(result1);
		    }   
		}
		List<String> deduped = list1.stream().distinct().collect(Collectors.toList());
		return deduped;
	}
}
