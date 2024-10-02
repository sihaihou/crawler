package com.reyco.crawler.endpoint;

import javax.xml.ws.Endpoint;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import com.reyco.crawler.properties.CrawlerProperties;

@ConditionalOnWebApplication
@ConditionalOnClass(Endpoint.class)
@ConditionalOnProperty(name="reyco.dasbx.crawer.enabled",matchIfMissing=true)
public class CrawlerEndpointAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	public CrawlerEndpoint crawlerEndpoint(CrawlerProperties crawlerProperties) {
		CrawlerEndpoint crawlerEndpoint = new CrawlerEndpoint(crawlerProperties);
		return crawlerEndpoint;
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledHealthIndicator("crawler")
	public CrawlerHealthIndicator crawlerHealthIndicator(CrawlerProperties crawlerProperties) {
		CrawlerHealthIndicator crawlerHealthIndicator = new CrawlerHealthIndicator(crawlerProperties);
		return crawlerHealthIndicator;
	}
	
}
