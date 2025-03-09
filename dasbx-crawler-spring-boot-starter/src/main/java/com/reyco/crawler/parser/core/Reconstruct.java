package com.reyco.crawler.parser.core;

import java.util.Map;

public interface Reconstruct {
	
	String reconstructUrl(String CrawlerUrl,Map<String,Object> param);
	
}
