package com.reyco.crawler.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;

import com.reyco.crawler.DasbxCrawlerManager;
import com.reyco.crawler.WebDriverHolder;
import com.reyco.crawler.core.factory.WebDriverFactory;
import com.reyco.crawler.exception.CannotGetWebDriverException;
import com.reyco.crawler.exception.CrawlerException;

public class CrawlerWebDriverUtils {
	
	private static final Log logger = LogFactory.getLog(CrawlerWebDriverUtils.class);
	
	public static WebDriver getWebDriver(WebDriverFactory webDriverFactory) throws CannotGetWebDriverException {
		try {
			return doGetWebDriver(webDriverFactory);
		} catch (Exception ex) {
			throw new CannotGetWebDriverException("Failed to obtain Web Driver", ex);
		}
	}
	
	private static WebDriver doGetWebDriver(WebDriverFactory webDriverFactory) throws CannotGetWebDriverException{
		WebDriverHolder webDriverHolder = (WebDriverHolder)DasbxCrawlerManager.getResource();
		if (webDriverHolder != null && webDriverHolder.hasWebDriver()){
			return webDriverHolder.getWebDriver();
		}
		WebDriver wd = fetchWebDriver(webDriverFactory);
		try {
			WebDriverHolder holderToUse = webDriverHolder;
			if(holderToUse==null) {
				holderToUse = new WebDriverHolder(wd);
			}else {
				holderToUse.setWebDriver(wd);
			}
			if (holderToUse != webDriverHolder) {
				DasbxCrawlerManager.bindResource(holderToUse);
			}
		} catch (Exception ex) {
			releaseWebDriver(wd);
			throw ex;
		}
		return wd;
	}
	private static void releaseWebDriver(WebDriver wd){
		try {
			doReleaseWebDriver(wd);
		}catch (Exception ex) {
			logger.debug("Could not quit WebDriver", ex);
		}catch (Throwable ex) {
			logger.debug("Unexpected exception on quiting WebDriver", ex);
		}
	}
	private static void doReleaseWebDriver(WebDriver wd) throws CrawlerException {
		doQuitWebDriver(wd);
	}
	private static void doQuitWebDriver(WebDriver wd) throws CrawlerException{
		wd.quit();
	}
	private static WebDriver fetchWebDriver(WebDriverFactory webDriverFactory) {
		return webDriverFactory.createWebDriver();
	}
	
}
