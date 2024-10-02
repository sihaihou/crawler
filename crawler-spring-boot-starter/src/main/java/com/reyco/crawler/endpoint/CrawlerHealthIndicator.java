package com.reyco.crawler.endpoint;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;

import com.reyco.crawler.properties.CrawlerProperties;

public class CrawlerHealthIndicator extends AbstractHealthIndicator{
	
	private final CrawlerProperties properties;
	
	public CrawlerHealthIndicator(CrawlerProperties properties) {
		super();
		this.properties = properties;
	}
	
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		if(StringUtils.isBlank(properties.getName()) || StringUtils.isBlank(properties.getDriver())) {
			builder.down().withDetail("webDriverName", properties.getName());
			builder.withDetail("webDriverPath", properties.getDriver());
		}else {
			File file = new File(properties.getDriver());
			if(file.exists() && file.isFile()) {
				builder.up().withDetail("webDriverName", properties.getName());
				builder.withDetail("webDriverPath", properties.getDriver());
			}else {
				builder.down().withDetail("webDriverPath",properties.getDriver()+",It's not a driver file!");
			}
		}
	}

}
