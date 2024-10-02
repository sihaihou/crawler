package com.reyco.crawler.parser.core.locate;

import java.util.List;

public interface CrawlerPageLocation {
	/**
	 * 详情/列表  默认详情
	 * @return
	 */
	default boolean isDetail() {
		return true;
	};
	/**
	 * 获取定位器
	 * @return
	 */
	List<CrawlerPageLocater> getCrawlerPageLocaters();
}
