package com.reyco.crawler;

import org.springframework.lang.Nullable;

public class DefaultCrawlerStatus implements CrawlerStatus{
	@Nullable
	private Object crawler;

	private boolean newCrawler;
	
	private boolean debug;
	
	public DefaultCrawlerStatus(Object crawler, boolean newCrawler,boolean debug) {
		super();
		this.crawler = crawler;
		this.newCrawler = newCrawler;
		this.debug = debug;
	}

	public Object getCrawler() {
		return crawler;
	}
	
	public boolean hasCrawler() {
		return (this.crawler != null);
	}
	
	public boolean isNewCrawler() {
		return newCrawler;
	}

	public void setNewCrawler(boolean newCrawler) {
		this.newCrawler = newCrawler;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
}
