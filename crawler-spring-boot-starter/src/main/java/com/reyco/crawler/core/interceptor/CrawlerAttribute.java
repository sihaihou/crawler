package com.reyco.crawler.core.interceptor;

import org.springframework.lang.Nullable;

public interface CrawlerAttribute {
	
	String getName();
	
	String getValue();
	
	@Nullable
	String getQualifier();
	
}
