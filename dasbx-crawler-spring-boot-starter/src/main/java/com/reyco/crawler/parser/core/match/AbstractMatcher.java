package com.reyco.crawler.parser.core.match;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractMatcher implements Matcher {
	
	protected String signatureInfo;
	
	private Map<String, Object> values = new HashMap<String, Object>();
	
	@Override
	public Object getValue(String key){
		return values.get(key);
	}
	
	protected void setValue(String name,String str) throws Exception {
		if(StringUtils.isBlank(str)) {
			throw new Exception("Configuration Error. config:["+signatureInfo+"]");
		}
		if(str.startsWith("'") && str.endsWith("'")) {
			String value = str.substring(1,str.length()-1);
			if(StringUtils.isBlank(value) || (value.length()!=1 && value.contains("'"))) {
				throw new Exception("Configuration Error. config:["+signatureInfo+"]");
			}
			if(name.endsWith("Index") || name.endsWith("index")) {
				int v = Integer.parseInt(value);
				if(v<0) {
					throw new Exception("Configuration Error. config:["+signatureInfo+"].Index greater than or greater than 0");
				}
				values.put(name,v);
			}else {
				values.put(name,value);
			}
		}else if(str.contains("'")) {
			throw new Exception("Configuration Error. config:["+signatureInfo+"]");
		}else {
			if(name.endsWith("Index") || name.endsWith("index")) {
				int v = Integer.parseInt(str);
				if(v<0) {
					throw new Exception("Configuration Error. config:["+signatureInfo+"].Index greater than or greater than 0");
				}
				values.put(name,v);
			}else {
				values.put(name,str);
			}
		}
	}
}
