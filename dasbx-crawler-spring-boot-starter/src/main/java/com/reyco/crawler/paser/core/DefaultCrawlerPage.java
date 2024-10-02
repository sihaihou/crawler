package com.reyco.crawler.paser.core;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.DasbxCrawlerManager;
import com.reyco.crawler.WebDriverHolder;
import com.reyco.crawler.annotation.Crawler;
import com.reyco.crawler.parser.core.exception.CrawlerPageException;
import com.reyco.crawler.parser.core.parse.CrawlerPageLocationParser;

public class DefaultCrawlerPage implements CrawlerPage {
	
	private CrawlerPageLocationParser crawlerLocationParser;

	private Reconstruct reconstruct;
	
	public DefaultCrawlerPage() {
	}
	public DefaultCrawlerPage(CrawlerPageLocationParser crawlerLocationParser, Reconstruct reconstruct) {
		super();
		this.crawlerLocationParser = crawlerLocationParser;
		this.reconstruct = reconstruct;
	}

	@Crawler
	@Override
	public List<Map<String, Object>> crawler(CrawlerPageParameter crawlerParameter) throws CrawlerPageException {
		prepare(crawlerParameter);
		invokeBefore(crawlerParameter);
		List<Map<String, Object>> o = invoke(crawlerParameter);
		invokeAfter(crawlerParameter);
		return o;
	}

	private final List<Map<String, Object>> invoke(CrawlerPageParameter crawlerParameter) throws CrawlerPageException {
		WebDriver webDriver = getWebDriver();
		String reconstructUrl = reconstruct.reconstructUrl(crawlerParameter.getCrawlerUrl(), crawlerParameter.getParam());
		webDriver.get(reconstructUrl);
		try {
			Thread.sleep(crawlerParameter.getLoadDelayTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> parse = crawlerLocationParser.parse(webDriver,crawlerParameter.getCrawlerPageLocation());
		return parse;
	}
	private WebDriver getWebDriver() {
		WebDriverHolder webDriverHolder = (WebDriverHolder)DasbxCrawlerManager.getResource();
		WebDriver webDriver = webDriverHolder.getWebDriver();
		return webDriver;
	}
	private void invokeBefore(CrawlerPageParameter crawlerParameter) {
	}
	
	private void invokeAfter(CrawlerPageParameter crawlerParameter) {
	}
	
	protected void prepare(CrawlerPageParameter crawlerParameter) {
	}
	public CrawlerPageLocationParser getCrawlerLocationParser() {
		return crawlerLocationParser;
	}
	public void setCrawlerLocationParser(CrawlerPageLocationParser crawlerLocationParser) {
		this.crawlerLocationParser = crawlerLocationParser;
	}
	public Reconstruct getReconstruct() {
		return reconstruct;
	}
	public void setReconstruct(Reconstruct reconstruct) {
		this.reconstruct = reconstruct;
	}
}
