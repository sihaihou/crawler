package com.reyco.crawler.parser.core.match;

public interface Matcher {
	
	public static final String TYPE_NAME = "type";
	
	public static final String ATTRIBUTE_NAME = "attributeName";
	
	public static final String DELIMITER_NAME = "delimiter";
	
	public static final String START_INDEX = "startIndex";
	
	public static final String END_INDEX = "endIndex";
	
	boolean matches(String s) throws Exception ;
	
	Object getValue(String key);
}
