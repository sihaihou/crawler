package com.reyco.crawler.core.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.aop.support.AopUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public abstract class AbstractCrawlerAttributeSource implements CrawlerAttributeSource {

	@Override
	public CrawlerAttribute getCrawlerAttribute(Method method, Class<?> targetClass) {
		if (method.getDeclaringClass() == Object.class) {
			return null;
		}
		return computeCrawlerAttribute(method, targetClass);
	}
	protected CrawlerAttribute computeCrawlerAttribute(Method method, @Nullable Class<?> targetClass) {
		if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
			return null;
		}
		Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
		CrawlerAttribute txAttr = findCrawlerAttribute(specificMethod);
		if (txAttr != null) {
			return txAttr;
		}
		txAttr = findCrawlerAttribute(specificMethod.getDeclaringClass());
		if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
			return txAttr;
		}
		if (specificMethod != method) {
			txAttr = findCrawlerAttribute(method);
			if (txAttr != null) {
				return txAttr;
			}
			txAttr = findCrawlerAttribute(method.getDeclaringClass());
			if (txAttr != null && ClassUtils.isUserLevelMethod(method)) {
				return txAttr;
			}
		}
		return null;
	}

	protected abstract CrawlerAttribute findCrawlerAttribute(Class<?> declaringClass);

	protected abstract CrawlerAttribute findCrawlerAttribute(Method method);

	protected boolean allowPublicMethodsOnly() {
		return false;
	}
}
