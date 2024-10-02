package com.reyco.crawler.paser.core;

import java.util.Map;

import com.reyco.crawler.parser.core.locate.CrawlerPageLocation;

public interface CrawlerPageParameter {
	/**
	 * 参数
	 * @return
	 */
	Map<String,Object> getParam();
	/**
	 * 获取爬虫页面
	 * @return
	 */
	String getCrawlerUrl();
	/**
	 * 加载延时时间  单位/毫秒
	 * @return
	 */
	default Long getLoadDelayTime() {
		return 10L;
	}
	/**
	 * 
	 * @return
	 */
	CrawlerPageLocation getCrawlerPageLocation();
}
