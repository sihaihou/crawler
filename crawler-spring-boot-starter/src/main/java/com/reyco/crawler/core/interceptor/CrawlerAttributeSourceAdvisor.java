package com.reyco.crawler.core.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

public class CrawlerAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -231868170618895946L;

	private CrawlerAttributeSource crawlerAttributeSource;

	private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			CrawlerAttributeSource sas = getCrawlerAttributeSource();
			return (sas == null || sas.getCrawlerAttribute(method, targetClass) != null);
		}

		public CrawlerAttributeSource getCrawlerAttributeSource() {
			return crawlerAttributeSource;
		}
	};

	public void setCrawlerAttributeSource(CrawlerAttributeSource crawlerAttributeSource) {
		this.crawlerAttributeSource = crawlerAttributeSource;
	}

	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

}
