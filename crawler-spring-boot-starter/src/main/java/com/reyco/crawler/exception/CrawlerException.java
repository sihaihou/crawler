package com.reyco.crawler.exception;

public class CrawlerException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2231603424049448792L;

	public CrawlerException(String msg) {
		super(msg);
	}

	public CrawlerException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public CrawlerException(Exception ex) {
		super(ex);
	}
}
