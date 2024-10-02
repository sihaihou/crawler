package com.reyco.crawler.core.parser;

import java.lang.reflect.AnnotatedElement;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import com.reyco.crawler.annotation.Crawler;
import com.reyco.crawler.core.interceptor.CrawlerAttribute;
import com.reyco.crawler.core.interceptor.DefaultCrawlerAttribute;

public class DefaultCrawlerAnnotationParser implements CrawlerAnnotationParser {

	@Override
	public CrawlerAttribute parseCrawlerAnnotation(AnnotatedElement element) {
		AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(element, Crawler.class, false, false);
		if (attributes != null) {
			return parseReycoResourceAttribute(attributes);
		}else {
			return null;
		}
	}

	private CrawlerAttribute parseReycoResourceAttribute(AnnotationAttributes attributes) {
		DefaultCrawlerAttribute dsa = new DefaultCrawlerAttribute();
		String value = attributes.getString("value");
		String crawlerManager = attributes.getString("crawlerManager");
		dsa.setName(value);
		dsa.setValue(value);
		dsa.setQualifier(crawlerManager);
		return dsa;
	}

}
