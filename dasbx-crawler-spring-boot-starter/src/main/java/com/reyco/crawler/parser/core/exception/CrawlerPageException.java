package com.reyco.crawler.parser.core.exception;

public class CrawlerPageException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7835827741800813123L;
	public CrawlerPageException() {
		this("Crawler Exception.");
	}
	public CrawlerPageException(String msg) {
		super(msg);
	}
	public CrawlerPageException(Exception exception) {
		super(exception);
	}
	
}
