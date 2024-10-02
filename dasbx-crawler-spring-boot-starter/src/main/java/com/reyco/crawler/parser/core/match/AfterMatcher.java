package com.reyco.crawler.parser.core.match;

import org.apache.commons.lang3.StringUtils;

public class AfterMatcher extends AbstractMatcher{
	
	public static final String TYPE = "after";
	
	private static final String PREFIX = "substring-after(";
	
	private static final String SUFFIX = ")";
	
	private String prefix = PREFIX;
	
	private String suffix = SUFFIX;
	
	public AfterMatcher() {
		try {
			setValue(TYPE_NAME, TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public AfterMatcher(String prefix, String suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		try {
			setValue(TYPE_NAME, TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean matches(String s) throws Exception {
		if(StringUtils.isBlank(s)) {
			return false;
		}
		this.signatureInfo = s;
		boolean matched = false;
		if(s.startsWith(prefix) && s.endsWith(suffix)) {
			init(s);
			matched = true;
		}
		return matched;
	}
	private void init(String str) throws Exception{
		String dest = str.substring(str.indexOf(prefix)+prefix.length(), str.indexOf(suffix));
		if(StringUtils.isBlank(str)) {
			throw new Exception("Configuration Error. config:["+signatureInfo+"]");
		}
		String[] array = dest.split(",");
		if(array.length!=2) {
			throw new Exception("Configuration Error. config:["+signatureInfo+"]");
		}
		setValue(ATTRIBUTE_NAME, array[0]);
		setValue(DELIMITER_NAME, array[1]);
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
