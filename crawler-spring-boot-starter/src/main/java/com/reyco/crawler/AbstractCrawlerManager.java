package com.reyco.crawler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;

import com.reyco.crawler.core.interceptor.CrawlerAttribute;
import com.reyco.crawler.core.interceptor.DefaultCrawlerAttribute;
import com.reyco.crawler.exception.CrawlerException;

public abstract class AbstractCrawlerManager implements CrawlerManager{
	
	protected transient Log logger = LogFactory.getLog(getClass());
	
	@Override
	public CrawlerStatus getCrawler(@Nullable CrawlerAttribute crawlerAttribute) throws CrawlerException {
		Object crawler = doGetCrawler();

		boolean debugEnabled = logger.isDebugEnabled();

		if (crawlerAttribute == null) {
			crawlerAttribute = new DefaultCrawlerAttribute();
		}

		if (isExistingCrawler(crawler)) {
			return handleExistingCrawler(crawler, debugEnabled);
		}else {
			DefaultCrawlerStatus status = newCrawlerStatus(crawler, true, debugEnabled);
			doOpen(crawler, crawlerAttribute);
			return status;
		}
	}
	
	private CrawlerStatus handleExistingCrawler(Object crawler, boolean debugEnabled)throws CrawlerException {
		return prepareCrawlerStatus(crawler, false, debugEnabled);
	}
	
	protected final DefaultCrawlerStatus prepareCrawlerStatus(@Nullable Object crawler, boolean newCrawler,boolean debug) {
		DefaultCrawlerStatus status = newCrawlerStatus(crawler, newCrawler,debug);
		return status;
	}
	
	protected DefaultCrawlerStatus newCrawlerStatus(@Nullable Object crawler, boolean newCrawler,boolean debug) {
		return new DefaultCrawlerStatus(crawler, newCrawler, debug);
	}
	
	@Override
	public void quit(@Nullable CrawlerStatus status) throws CrawlerException {
		DefaultCrawlerStatus defStatus = (DefaultCrawlerStatus)status;
		processQuit(defStatus);
	}
	private void processQuit(DefaultCrawlerStatus status) {
		if (status.isNewCrawler()) {
			triggerBeforeCompletion(status);
			try {
				doQuit(status);
			}catch (CrawlerException | Error ex) {
				logger.error("WebDriver quit exception."+ex);
				throw ex;
			}
		}
	}
	private void triggerBeforeCompletion(DefaultCrawlerStatus defStatus) {
		try {
			DasbxCrawlerManager.unbindResource();
		} catch (Exception e) {
			logger.error("WebDriver unBind resource exception."+e);
			e.printStackTrace();
		}
	}
	protected abstract void doQuit(DefaultCrawlerStatus status) throws CrawlerException;
	
	protected abstract Object doGetCrawler() throws CrawlerException;
	
	
	protected boolean isExistingCrawler(Object crawler) throws CrawlerException {
		return false;
	}
	
	protected abstract void doOpen(Object crawler, CrawlerAttribute crawlerAttribute)throws CrawlerException;
}
