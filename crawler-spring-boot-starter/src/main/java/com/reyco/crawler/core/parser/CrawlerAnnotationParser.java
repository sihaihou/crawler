package com.reyco.crawler.core.parser;

import java.lang.reflect.AnnotatedElement;

import com.reyco.crawler.core.interceptor.CrawlerAttribute;

public interface CrawlerAnnotationParser {
	
	CrawlerAttribute parseCrawlerAnnotation(AnnotatedElement element);
	
}
