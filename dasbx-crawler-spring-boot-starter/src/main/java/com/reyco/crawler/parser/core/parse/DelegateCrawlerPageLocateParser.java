package com.reyco.crawler.parser.core.parse;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocater;

public class DelegateCrawlerPageLocateParser implements CrawlerPageLocateParser{
	
	private List<CrawlerPageLocateParser> crawlerPageLocateParsers;
	
	private CrawlerPageLocateParser defaultCrawlerPageLocateParser;
	
	public DelegateCrawlerPageLocateParser() {
	}
	
	public DelegateCrawlerPageLocateParser(List<CrawlerPageLocateParser> crawlerPageLocateParsers,
			CrawlerPageLocateParser defaultCrawlerPageLocateParser) {
		super();
		this.crawlerPageLocateParsers = crawlerPageLocateParsers;
		this.defaultCrawlerPageLocateParser = defaultCrawlerPageLocateParser;
	}

	@Override
	public Enter parse(WebDriver webDriver, CrawlerPageLocater crawlerPageLocater, Boolean detail)
			throws CrawlerPageLocateParseException {
		CrawlerPageLocateParser crawlerPageLocateParser = getMatchCrawlerPageLocateParser(crawlerPageLocater);
		return crawlerPageLocateParser.parse(webDriver, crawlerPageLocater, detail);
	}
	/**
	 * 获取匹配的解析器
	 * @param crawlerPageLocater
	 * @return
	 */
	private CrawlerPageLocateParser getMatchCrawlerPageLocateParser(CrawlerPageLocater crawlerPageLocater) {
		for (CrawlerPageLocateParser crawlerPageLocateParser : crawlerPageLocateParsers) {
			if(crawlerPageLocateParser.support(crawlerPageLocater.getType())) {
				return crawlerPageLocateParser;
			}
		}
		return defaultCrawlerPageLocateParser;
	}

	public List<CrawlerPageLocateParser> getCrawlerPageLocateParsers() {
		return crawlerPageLocateParsers;
	}
	public void setCrawlerPageLocateParsers(List<CrawlerPageLocateParser> crawlerPageLocateParsers) {
		this.crawlerPageLocateParsers = crawlerPageLocateParsers;
	}
	public CrawlerPageLocateParser getDefaultCrawlerPageLocateParser() {
		return defaultCrawlerPageLocateParser;
	}
	public void setDefaultCrawlerPageLocateParser(CrawlerPageLocateParser defaultCrawlerPageLocateParser) {
		this.defaultCrawlerPageLocateParser = defaultCrawlerPageLocateParser;
	}

}
