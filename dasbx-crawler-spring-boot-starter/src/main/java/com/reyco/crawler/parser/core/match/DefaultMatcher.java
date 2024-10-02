package com.reyco.crawler.parser.core.match;

import org.apache.commons.lang3.StringUtils;

public class DefaultMatcher extends AbstractMatcher {
	
	public static final String TYPE = "default";
	
	public DefaultMatcher() {
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
		setValue(ATTRIBUTE_NAME, s);
		return true;
	}
	
}
