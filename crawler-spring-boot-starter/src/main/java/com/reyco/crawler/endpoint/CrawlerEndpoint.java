package com.reyco.crawler.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import com.reyco.crawler.properties.CrawlerProperties;

@Endpoint(id = "crawler")
public class CrawlerEndpoint {
	
	private final CrawlerProperties properties;
	
	public CrawlerEndpoint(CrawlerProperties properties) {
		super();
		this.properties = properties;
	}
	
	@ReadOperation
	public Map<String, Object> invoke() {
		Map<String, Object> result = new HashMap<>(16);
		result.put("WebDriverName", properties.getName());
		result.put("WebDriverPath", properties.getDriver());
		return result;
	}
	
}
