package com.reyco.crawler;

import org.springframework.lang.Nullable;

import com.reyco.crawler.core.interceptor.CrawlerAttribute;
import com.reyco.crawler.exception.CrawlerException;

public interface CrawlerManager {
	
	CrawlerStatus getCrawler(@Nullable CrawlerAttribute crawlerAttribute) throws CrawlerException;
	
	void quit(@Nullable CrawlerStatus status) throws CrawlerException;
}
