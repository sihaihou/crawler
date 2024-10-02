package com.reyco.crawler.parser.core.exception;

public class CrawlerPageLocateParseException extends CrawlerPageException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887703610256859670L;
	public CrawlerPageLocateParseException() {
		this("CrawlerLocater Parse Exception.");
	}
	public CrawlerPageLocateParseException(String msg) {
		super(msg);
	}
	
}
