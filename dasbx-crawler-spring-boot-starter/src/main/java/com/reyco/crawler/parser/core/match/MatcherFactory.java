package com.reyco.crawler.parser.core.match;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;

public interface MatcherFactory {
	
	Matcher getMatcher(String s) throws CrawlerPageLocateParseException;
	
}
