package com.reyco.crawler.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(CrawlerProperties.CRAWLER_PREFIX)
public class CrawlerProperties {
	
	public static final String CRAWLER_PREFIX = "reyco.dasbx.crawler";
	
	private String name = "chrome";
	
	private String driver = "D:\\download\\chromedriver_win64\\chromedriver.exe";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
}
