package com.reyco.crawler;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.core.factory.WebDriverFactory;
import com.reyco.crawler.core.interceptor.CrawlerAttribute;
import com.reyco.crawler.exception.CrawlerException;

public class ReycoCrawlerManager extends AbstractCrawlerManager {
	
	private WebDriverFactory webDriverFactory;
	
	public ReycoCrawlerManager() {
	}
	
	public ReycoCrawlerManager(WebDriverFactory webDriverFactory) {
		super();
		this.webDriverFactory = webDriverFactory;
	}
	
	public void setWebDriverFactory(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}
	
	@Override
	protected Object doGetCrawler() throws CrawlerException {
		CrawlerObject txObject = new CrawlerObject();
		WebDriverHolder webDriverHolder = (WebDriverHolder) DasbxCrawlerManager.getResource();
		txObject.setWebDriverHolder(webDriverHolder, false);
		return txObject;
	}

	@Override
	protected void doOpen(Object crawler, CrawlerAttribute crawlerAttribute) throws CrawlerException {
		CrawlerObject cxObject = (CrawlerObject) crawler;
		WebDriver webDriver = null;

		try {
			if (!cxObject.hasWebDriverHolder()) {
				WebDriver newWebDriver = webDriverFactory.createWebDriver();
				if (logger.isDebugEnabled()) {
					logger.debug("Acquired WebDriver [" + newWebDriver + "] for crawler");
				}
				cxObject.setWebDriverHolder(new WebDriverHolder(newWebDriver), true);
			}

			webDriver = cxObject.getWebDriverHolder().getWebDriver();

			// Bind the webDriver holder to the thread.
			if (cxObject.isNewWebDriverHolder()) {
				DasbxCrawlerManager.bindResource(cxObject.getWebDriverHolder());
			}
		}catch (Throwable ex) {
			if (cxObject.isNewWebDriverHolder()) {
				webDriver.quit();
				cxObject.setWebDriverHolder(null, false);
			}
			throw new CrawlerException("Could not open WebDriver for crawler", ex);
		}
	}
	
	@Override
	protected void doQuit(DefaultCrawlerStatus status) throws CrawlerException {
		CrawlerObject cxObject = (CrawlerObject) status.getCrawler();
		WebDriver webDriver = cxObject.getWebDriverHolder().getWebDriver();
		if (status.isDebug()) {
			logger.debug("Quiting Web rawler on WebDriver [" + webDriver + "]");
		}
		try {
			webDriver.quit();
		}catch (Exception ex) {
			throw new CrawlerException("Could not quit Web rawler", ex);
		}
	}
	
	@Override
	protected boolean isExistingCrawler(Object crawler) throws CrawlerException {
		CrawlerObject cxObject = (CrawlerObject) crawler;
		return cxObject.hasWebDriverHolder();
	}

}
