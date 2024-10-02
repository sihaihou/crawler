package com.reyco.crawler.parser.core.locate;

/**
 * 定位器
 * @author reyco
 *
 */
public interface CrawlerPageLocater {
	/**
	 * 名称
	 * @return
	 */
	String getName();
	/**
	 * 定位方式：id、name、className、tagName、LinkText、partialLinkText、xpath、cssSelector
	 * @return
	 */
	default LocateMode getMode() {
		return LocateMode.XPATH;
	}
	/**
	 * 表达式
	 * @return
	 */
	String getExpression();
	/**
	 * 属性：
	 * @return
	 */
	String getAttribute();
	
	default Object getType() {
		return true;
	}
}
