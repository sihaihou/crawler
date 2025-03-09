package com.reyco.crawler.paser.core;

import java.util.List;
import java.util.Map;

import com.reyco.crawler.parser.core.exception.CrawlerPageException;

public interface CrawlerPage {
	
	/**
	 * 一个map对应一条完整记录
	 * @param crawlerParameter
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> crawler(CrawlerPageParameter crawlerParameter) throws CrawlerPageException;
	
}
