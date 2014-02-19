package uk.co.larby.helpers;

import java.util.Iterator;

public class StringHelpers {
	/*
	 * Taken from here: http://stackoverflow.com/questions/63150/whats-the-best-way-to-build-a-string-of-delimited-items-in-java 
	 * and updated to support a list of integers.
	 */
	public static String Join(Iterable<?> s, String delimiter) {
	    Iterator<?> iter = s.iterator();
	    
	    if (!iter.hasNext()) 
	    	return "";
	    
	    StringBuilder buffer = new StringBuilder(iter.next().toString());
	    
	    while (iter.hasNext()) 
	    	buffer.append(delimiter).append(iter.next());
	    
	    return buffer.toString();
	}
	
	public static String JoinAddOne(Iterable<Integer> s, String delimiter) {
	    Iterator<Integer> iter = s.iterator();
	    
	    if (!iter.hasNext()) 
	    	return "";
	    
	    StringBuilder buffer = new StringBuilder(String.format("%s", (iter.next() + 1)));
	    
	    while (iter.hasNext()) 
	    	buffer.append(delimiter).append(iter.next() + 1);
	    
	    return buffer.toString();
	}
}
