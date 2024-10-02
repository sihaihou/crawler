package com.reyco.crawler.core.interceptor;

import java.lang.reflect.Method;

import org.springframework.lang.Nullable;

public interface CrawlerAttributeSource {
	
	@Nullable
	CrawlerAttribute getCrawlerAttribute(Method method, @Nullable Class<?> targetClass);
	
}
