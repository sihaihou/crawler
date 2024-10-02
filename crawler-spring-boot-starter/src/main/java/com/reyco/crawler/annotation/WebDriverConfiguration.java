package com.reyco.crawler.annotation;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reyco.crawler.ReycoCrawlerManager;
import com.reyco.crawler.core.factory.DefaultWebDriverFactory;
import com.reyco.crawler.core.factory.WebDriverFactory;
import com.reyco.crawler.properties.CrawlerProperties;

@Configuration
@ConditionalOnProperty(name="reyco.dasbx.crawler.enabled",matchIfMissing=true)
@EnableConfigurationProperties(CrawlerProperties.class)
@AutoConfigureBefore(value={ProxyCrawlerManagementConfiguration.class})
public class WebDriverConfiguration {
	
	@Bean
	@ConditionalOnMissingBean(CrawlerProperties.class)
	public CrawlerProperties crawlerProperties() {
		CrawlerProperties crawlerProperties = new CrawlerProperties();
		return crawlerProperties;
	}
	
	@Bean
	@ConditionalOnMissingBean(WebDriverFactory.class)
	public WebDriverFactory webDriverFactory(CrawlerProperties crawlerProperties) {
		DefaultWebDriverFactory webDriverFactory = new DefaultWebDriverFactory(crawlerProperties);
		return webDriverFactory;
	}
	
	@Bean
	@ConditionalOnMissingBean(ReycoCrawlerManager.class)
	public ReycoCrawlerManager crawlerManager(WebDriverFactory webDriverFactory) throws Exception {
		ReycoCrawlerManager crawlerManager = new ReycoCrawlerManager(webDriverFactory);
		return crawlerManager;
	}
}
