package com.reyco.crawler.parser.core.autoconfiguration;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reyco.crawler.annotation.EnableCrawlerManagement;
import com.reyco.crawler.parser.core.match.DefaultMatcherFactory;
import com.reyco.crawler.parser.core.match.MatcherFactory;
import com.reyco.crawler.parser.core.parse.CrawlerPageLocateParser;
import com.reyco.crawler.parser.core.parse.CrawlerPageLocationParser;
import com.reyco.crawler.parser.core.parse.DefaultCrawlerPageLocateParser;
import com.reyco.crawler.parser.core.parse.DefaultCrawlerPageLocationParser;
import com.reyco.crawler.parser.core.parse.DelegateCrawlerPageLocateParser;
import com.reyco.crawler.paser.core.CrawlerPage;
import com.reyco.crawler.paser.core.DefaultCrawlerPage;
import com.reyco.crawler.paser.core.DefaultReconstruct;
import com.reyco.crawler.paser.core.Reconstruct;


@Configuration
@EnableCrawlerManagement
@ConditionalOnProperty(name="reyco.dasbx.crawer.parser.enabled",matchIfMissing=true)
public class CrawlerParserConfiguration {
	
	@Bean
	public CrawlerPage crawlerPage(CrawlerPageLocationParser crawlerLocationParser,Reconstruct reconstruct) {
		DefaultCrawlerPage crawlerPage = new DefaultCrawlerPage();
		crawlerPage.setCrawlerLocationParser(crawlerLocationParser);
		crawlerPage.setReconstruct(reconstruct);
		return crawlerPage;
	}
	@Bean
	public CrawlerPageLocationParser crawlerLocationParser(DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParse) {
		DefaultCrawlerPageLocationParser crawlerPageLocationParser = new DefaultCrawlerPageLocationParser();
		crawlerPageLocationParser.setDelegateCrawlerPageLocateParse(delegateCrawlerPageLocateParse);
		return crawlerPageLocationParser;
	}
	@Bean
	public DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParser(List<CrawlerPageLocateParser> crawlerPageLocateParsers,@Qualifier("defaultCrawlerPageLocateParser")CrawlerPageLocateParser defaultCrawlerPageLocateParser) {
		DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParser = new DelegateCrawlerPageLocateParser();
		delegateCrawlerPageLocateParser.setCrawlerPageLocateParsers(crawlerPageLocateParsers);
		delegateCrawlerPageLocateParser.setDefaultCrawlerPageLocateParser(defaultCrawlerPageLocateParser);
		return delegateCrawlerPageLocateParser;
	}
	@Bean("defaultCrawlerPageLocateParser")
	public CrawlerPageLocateParser crawlerPageLocateParse(MatcherFactory matcherFactory) {
		DefaultCrawlerPageLocateParser crawlerPageLocateParse = new DefaultCrawlerPageLocateParser();
		crawlerPageLocateParse.setMatcherFactory(matcherFactory);
		return crawlerPageLocateParse;
	}
	
	@Bean
	@ConditionalOnMissingBean(MatcherFactory.class)
	public MatcherFactory matcherFactory() {
		return new DefaultMatcherFactory();
	}
	
	@Bean
	@ConditionalOnMissingBean(Reconstruct.class)
	public Reconstruct reconstruct() {
		Reconstruct reconstruct = new DefaultReconstruct();
		return reconstruct;
	}
}
