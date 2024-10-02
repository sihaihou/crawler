package com.reyco.crawler.exception;

public class CannotGetWebDriverException extends CrawlerException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7357831451891322870L;

	public CannotGetWebDriverException(String msg) {
		super(msg);
	}

	public CannotGetWebDriverException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
