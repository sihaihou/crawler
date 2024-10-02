package com.reyco.crawler;

public class CrawlerObject {
	
	private boolean newWebDriverHolder;
	
	private WebDriverHolder webDriverHolder;

	public WebDriverHolder getWebDriverHolder() {
		return webDriverHolder;
	}

	public void setWebDriverHolder(WebDriverHolder webDriverHolder,boolean newWebDriverHolder) {
		this.webDriverHolder = webDriverHolder;
		this.newWebDriverHolder = newWebDriverHolder;
	}
	public boolean isNewWebDriverHolder() {
		return this.newWebDriverHolder;
	}
	public boolean hasWebDriverHolder() {
		return this.webDriverHolder!=null;
	}
	
}
