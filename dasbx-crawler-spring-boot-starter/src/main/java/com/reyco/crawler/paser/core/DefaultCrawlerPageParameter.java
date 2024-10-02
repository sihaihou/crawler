package com.reyco.crawler.paser.core;

import java.util.Map;

import com.reyco.crawler.parser.core.locate.CrawlerPageLocation;

public class DefaultCrawlerPageParameter implements CrawlerPageParameter{
	private String crawlerUrl;
	private Map<String, Object> param;
	private Long loadDelayTime = 10L;
	private CrawlerPageLocation crawlerPageLocation;
	@Override
	public String getCrawlerUrl() {
		return this.crawlerUrl;
	}
	public void setCrawlerUrl(String crawlerUrl) {
		this.crawlerUrl = crawlerUrl;
	}
	@Override
	public Long getLoadDelayTime() {
		return this.loadDelayTime;
	}
	public void setLoadDelayTime(Long loadDelayTime) {
		this.loadDelayTime = loadDelayTime;
	}
	@Override
	public CrawlerPageLocation getCrawlerPageLocation() {
		return this.crawlerPageLocation;
	}
	public void setCrawlerPageLocation(CrawlerPageLocation crawlerPageLocation) {
		this.crawlerPageLocation = crawlerPageLocation;
	}
	@Override
	public Map<String, Object> getParam() {
		return this.param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
}
