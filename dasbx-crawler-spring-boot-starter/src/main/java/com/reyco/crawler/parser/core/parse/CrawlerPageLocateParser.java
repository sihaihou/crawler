package com.reyco.crawler.parser.core.parse;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocater;

public interface CrawlerPageLocateParser {
	
	default boolean support(Object type){
		return false;
	}
	
	Enter parse(WebDriver webDriver,CrawlerPageLocater crawlerPageLocater,Boolean detail) throws CrawlerPageLocateParseException;
	
}
