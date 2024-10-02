package com.reyco.crawler.annotation;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reyco.crawler.core.interceptor.AnnotationCrawlerAttributeSource;
import com.reyco.crawler.core.interceptor.CrawlerAttributeSource;
import com.reyco.crawler.core.interceptor.CrawlerAttributeSourceAdvisor;
import com.reyco.crawler.core.interceptor.CrawlerInterceptor;

@Configuration
@AutoConfigureAfter(WebDriverConfiguration.class)
public class ProxyCrawlerManagementConfiguration extends AbstractCrawlerManagementConfiguration {
	
	@Bean
	public CrawlerAttributeSourceAdvisor transactionAdvisor() {
		CrawlerAttributeSourceAdvisor advisor = new CrawlerAttributeSourceAdvisor();
		advisor.setCrawlerAttributeSource(crawlerAttributeSource());
		advisor.setAdvice(crawlerInterceptor());
		return advisor;
	}
	
	@Bean
	public CrawlerAttributeSource crawlerAttributeSource() {
		return new AnnotationCrawlerAttributeSource();
	}
	
	@Bean
	public CrawlerInterceptor crawlerInterceptor() {
		CrawlerInterceptor interceptor = new CrawlerInterceptor();
		interceptor.setCrawlerAttributeSource(crawlerAttributeSource());
		if (this.cxManager != null) {
			interceptor.setCrawlerManager(this.cxManager);
		}
		return interceptor;
	}
}
