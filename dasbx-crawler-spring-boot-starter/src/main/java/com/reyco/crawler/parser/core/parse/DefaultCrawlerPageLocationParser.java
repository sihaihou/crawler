package com.reyco.crawler.parser.core.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;
import com.reyco.crawler.parser.core.exception.CrawlerPageLocationParseException;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocater;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocation;

public class DefaultCrawlerPageLocationParser implements CrawlerPageLocationParser {
	
	private DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParse;
	
	public DefaultCrawlerPageLocationParser() {
	}

	public DefaultCrawlerPageLocationParser(DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParse) {
		super();
		this.delegateCrawlerPageLocateParse = delegateCrawlerPageLocateParse;
	}

	@Override
	public List<Map<String, Object>> parse(WebDriver webDriver, CrawlerPageLocation crawlerPageLocation) throws CrawlerPageLocationParseException, CrawlerPageLocateParseException {
		if(crawlerPageLocation==null) {
			return null;
		}
		List<CrawlerPageLocater> crawlerPageLocaters = crawlerPageLocation.getCrawlerPageLocaters();
		if(crawlerPageLocaters!=null) {
			List<String> names = new ArrayList<>();
			Map<String,List<Object>> map = new HashMap<String,List<Object>>();
			int size = 0;
			for (CrawlerPageLocater crawlerPageLocater : crawlerPageLocaters) {
				String name = crawlerPageLocater.getName();
				names.add(name);
				Enter enter = delegateCrawlerPageLocateParse.parse(webDriver, crawlerPageLocater, crawlerPageLocation.isDetail());
				if(size==0) {
					size = enter.getValue().size();
				}
				map.put(enter.getKey(), enter.getValue());
			}
			List<Map<String,Object>> listMap = new ArrayList<>(size);
			Map<String,Object> item = null;
			for(int i=0;i<size;i++) {
				item = new HashMap<String,Object>();
				listMap.add(item);
				for(String name : names) {
					List<Object> list = map.get(name);
					Object value = list.get(i);
					item.put(name, value);
				}
			}
			return listMap;
		}
		return null;
	}

	public DelegateCrawlerPageLocateParser getDelegateCrawlerPageLocateParse() {
		return delegateCrawlerPageLocateParse;
	}

	public void setDelegateCrawlerPageLocateParse(DelegateCrawlerPageLocateParser delegateCrawlerPageLocateParse) {
		this.delegateCrawlerPageLocateParse = delegateCrawlerPageLocateParse;
	}
}
