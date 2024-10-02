package com.reyco.crawler.parser.core.locate;

import java.util.List;

public class DefaultCrawlerPageLocation implements CrawlerPageLocation{
	private boolean detail = true;
	private List<CrawlerPageLocater> crawlerPageLocaters;
	@Override
	public boolean isDetail() {
		return detail;
	}
	public void setDetail(boolean detail) {
		this.detail = detail;
	}
	@Override
	public List<CrawlerPageLocater> getCrawlerPageLocaters() {
		return crawlerPageLocaters;
	}
	public void setCrawlerPageLocaters(List<CrawlerPageLocater> crawlerPageLocaters) {
		this.crawlerPageLocaters = crawlerPageLocaters;
	}
}
