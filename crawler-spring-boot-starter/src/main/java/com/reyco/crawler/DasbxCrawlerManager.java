package com.reyco.crawler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DasbxCrawlerManager {
	
	private static final Log logger = LogFactory.getLog(DasbxCrawlerManager.class);
	
	private static ThreadLocal<Object> resource = new ThreadLocal<Object>();
	
	public static Object getResource() {
		Object value = resource.get();
		if (value != null && logger.isTraceEnabled()) {
			logger.trace("Retrieved value [" + value + "] bound to thread [" + Thread.currentThread().getName() + "]");
		}
		return value;
	}

	public static void bindResource(WebDriverHolder value) {
		resource.set(value);
		if (logger.isTraceEnabled()) {
			logger.trace("Bound value [" + value + "] to thread [" + Thread.currentThread().getName() + "]");
		}
	}

	public static void unbindResource() {
		Object value = resource.get();
		resource.remove();
		if (value == null) {
			throw new IllegalStateException("No value bound to thread [" + Thread.currentThread().getName() + "]");
		}
	}

}
