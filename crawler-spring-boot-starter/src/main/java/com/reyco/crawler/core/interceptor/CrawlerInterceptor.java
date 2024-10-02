package com.reyco.crawler.core.interceptor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;

import com.reyco.crawler.CrawlerManager;

public class CrawlerInterceptor extends CrawlerAspectSupport implements MethodInterceptor,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2736908278991975261L;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		return invokeWithinCrawler(invocation.getMethod(), targetClass, invocation::proceed);
	}
	
	public CrawlerInterceptor() {
	}
	
	public CrawlerInterceptor(CrawlerManager crawlerManager,CrawlerAttributeSource crawlerAttributeSource) {
		setCrawlerManager(crawlerManager);
		setCrawlerAttributeSource(crawlerAttributeSource);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(getCrawlerManager());
		oos.writeObject(getCrawlerManagerBeanName());
		oos.writeObject(getCrawlerAttributeSource());
		oos.writeObject(getBeanFactory());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		setCrawlerManagerBeanName((String) ois.readObject());
		setCrawlerManager((CrawlerManager)ois.readObject());
		setCrawlerAttributeSource((CrawlerAttributeSource) ois.readObject());
		setBeanFactory((BeanFactory) ois.readObject());
	}
}
