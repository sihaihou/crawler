package com.reyco.crawler.parser.core.exception;

public class CrawlerPageLocationParseException extends CrawlerPageException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887703610256859670L;
	public CrawlerPageLocationParseException() {
		this("CrawlerLocater Parse Exception.");
	}
	public CrawlerPageLocationParseException(String msg) {
		super(msg);
	}
}
