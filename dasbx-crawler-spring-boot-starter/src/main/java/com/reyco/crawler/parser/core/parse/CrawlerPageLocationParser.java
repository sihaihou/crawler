package com.reyco.crawler.parser.core.parse;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;
import com.reyco.crawler.parser.core.exception.CrawlerPageLocationParseException;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocation;

public interface CrawlerPageLocationParser{
	
	List<Map<String,Object>> parse(WebDriver webDriver,CrawlerPageLocation crawlerPageLocation) throws CrawlerPageLocationParseException,CrawlerPageLocateParseException;
}
