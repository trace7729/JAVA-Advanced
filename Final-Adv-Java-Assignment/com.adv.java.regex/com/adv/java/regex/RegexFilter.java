package com.adv.java.regex;

import java.io.IOException;
import java.util.List;

import com.adv.java.regex.internal.RegexFilterImpl;

public class RegexFilter {
	//call the RexFilterResult method of RegexFilterImpl class
	public List<String> RexOutput (String[] results) throws IOException {
		List<String> rexOutput = new RegexFilterImpl().RexFilterResults(results);
		return rexOutput;
	}
}
