package com.reyco.crawler.core.interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;

import com.reyco.crawler.CrawlerManager;
import com.reyco.crawler.CrawlerStatus;
import com.reyco.crawler.exception.CrawlerException;

public abstract class CrawlerAspectSupport implements BeanFactoryAware, InitializingBean {
	
	private static final Object DEFAULT_CRAWLER_MANAGER_KEY = new Object();
	
	private static final ThreadLocal<CrawlerInfo> CRAWLERINFO_THREADLOCAL =
			new NamedThreadLocal<>("Current aspect-driven Crawler");
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Nullable
	private String crawlerManagerBeanName;

	@Nullable
	private CrawlerManager crawlerManager;
	
	@Nullable
	private CrawlerAttributeSource crawlerAttributeSource;
	
	@Nullable
	private BeanFactory beanFactory;
	
	private final ConcurrentMap<Object, CrawlerManager> crawlerManagerCache = new ConcurrentReferenceHashMap<>(4);
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	public BeanFactory getBeanFactory() {
		return this.beanFactory;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		if (getCrawlerManager() == null && this.beanFactory == null) {
			throw new IllegalStateException(
					"Set the 'crawlerManager' property or make sure to run within a BeanFactory " +
					"containing a CrawlerManager bean!");
		}
		if (getCrawlerAttributeSource() == null) {
			throw new IllegalStateException(
					"Either 'crawlerAttributeSource' or 'crawlerAttributes' is required: " +
					"If there are no crawler methods, then don't use a crawler aspect.");
		}
	}
	protected Object invokeWithinCrawler(Method method, @Nullable Class<?> targetClass,
			final InvocationCallback invocation) throws Throwable {
		CrawlerAttributeSource cas = getCrawlerAttributeSource();
		final CrawlerAttribute cxAttr = (cas != null ? cas.getCrawlerAttribute(method, targetClass) : null);
		final CrawlerManager cm = determineCrawlerManager(cxAttr);
		final String joinpointIdentification = methodIdentification(method, targetClass, cxAttr);
		CrawlerInfo cxInfo = createCrawlerIfNecessary(cm, cxAttr, joinpointIdentification);
		Object retVal = null;
		try {
			retVal = invocation.proceedWithInvocation();
		}catch (Throwable ex) {
			completeCrawlerAfterThrowing(cxInfo, ex);
			throw ex;
		}finally {
			cleanupCrawlerInfo(cxInfo);
		}
		commitCrawlerAfterReturning(cxInfo);
		return retVal;
	}
	/**
	 * Clear the cache.
	 */
	protected void clearTransactionManagerCache() {
		this.crawlerManagerCache.clear();
		this.beanFactory = null;
	}

	@Nullable
	protected CrawlerManager determineCrawlerManager(@Nullable CrawlerAttribute txAttr) {
		if (txAttr == null || this.beanFactory == null) {
			return getCrawlerManager();
		}
		String qualifier = txAttr.getQualifier();
		if (StringUtils.hasText(qualifier)) {
			return determineQualifiedCrawlerManager(this.beanFactory, qualifier);
		}
		else if (StringUtils.hasText(this.crawlerManagerBeanName)) {
			return determineQualifiedCrawlerManager(this.beanFactory, this.crawlerManagerBeanName);
		}
		else {
			CrawlerManager defaultCrawlerManager = getCrawlerManager();
			if (defaultCrawlerManager == null) {
				defaultCrawlerManager = this.crawlerManagerCache.get(DEFAULT_CRAWLER_MANAGER_KEY);
				if (defaultCrawlerManager == null) {
					defaultCrawlerManager = this.beanFactory.getBean(CrawlerManager.class);
					this.crawlerManagerCache.putIfAbsent(DEFAULT_CRAWLER_MANAGER_KEY, defaultCrawlerManager);
				}
			}
			return defaultCrawlerManager;
		}
	}
	private CrawlerManager determineQualifiedCrawlerManager(BeanFactory beanFactory, String qualifier) {
		CrawlerManager txManager = this.crawlerManagerCache.get(qualifier);
		if (txManager == null) {
			txManager = BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, CrawlerManager.class, qualifier);
			this.crawlerManagerCache.putIfAbsent(qualifier, txManager);
		}
		return txManager;
	}
	private String methodIdentification(Method method, @Nullable Class<?> targetClass,@Nullable CrawlerAttribute cxAttr) {
		String methodIdentification = methodIdentification(method, targetClass);
		if (methodIdentification == null) {
			if (cxAttr instanceof DefaultCrawlerAttribute) {
				methodIdentification = ((DefaultCrawlerAttribute) cxAttr).getName();
			}
			if (methodIdentification == null) {
				methodIdentification = ClassUtils.getQualifiedMethodName(method, targetClass);
			}
		}
		return methodIdentification;
	}
	@Nullable
	protected String methodIdentification(Method method, @Nullable Class<?> targetClass) {
		return null;
	}
	
	protected CrawlerInfo createCrawlerIfNecessary(@Nullable CrawlerManager cm,
			@Nullable CrawlerAttribute cxAttr, final String joinpointIdentification) {
		if (cxAttr != null && cxAttr.getName() == null) {
			cxAttr = new DefaultCrawlerAttribute(joinpointIdentification);
		}

		CrawlerStatus status = null;
		if (cxAttr != null) {
			if (cm != null) {
				status = cm.getCrawler(cxAttr);
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("Skipping crawler joinpoint [" + joinpointIdentification + "] because no crawler manager has been configured");
				}
			}
		}
		return prepareCrawlerInfo(cm, cxAttr, joinpointIdentification, status);
	}
	protected CrawlerInfo prepareCrawlerInfo(@Nullable CrawlerManager cm,
			@Nullable CrawlerAttribute cxAttr, String joinpointIdentification,
			@Nullable CrawlerStatus status) {

		CrawlerInfo cxInfo = new CrawlerInfo(cm, cxAttr, joinpointIdentification);
		if (cxAttr != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Getting crawler for [" + cxInfo.getJoinpointIdentification() + "]");
			}
			cxInfo.newCrawlerStatus(status);
		}
		else {
			if (logger.isTraceEnabled()) {
				logger.trace("Don't need to create crawler for [" + joinpointIdentification + "]: This method isn't crawler.");
			}
		}
		cxInfo.bindToThread();
		return cxInfo;
	}
	
	protected void commitCrawlerAfterReturning(@Nullable CrawlerInfo cxInfo) {
		if (cxInfo != null && cxInfo.getCrawlerStatus() != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Completing crawler for [" + cxInfo.getJoinpointIdentification() + "]");
			}
			cxInfo.getCrawlerManager().quit(cxInfo.getCrawlerStatus());
		}
	}
	
	protected void completeCrawlerAfterThrowing(@Nullable CrawlerInfo cxInfo, Throwable ex) {
		if (cxInfo != null && cxInfo.getCrawlerStatus() != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Completing transaction for [" + cxInfo.getJoinpointIdentification() +
						"] after exception: " + ex);
			}
			try {
				cxInfo.getCrawlerManager().quit(cxInfo.getCrawlerStatus());
			}catch (CrawlerException ex2) {
				logger.error("Application exception overridden by quit exception", ex);
				throw ex2;
			}catch (RuntimeException | Error ex2) {
				logger.error("Application exception overridden by quit exception", ex);
				throw ex2;
			}
		}
	}
	
	protected void cleanupCrawlerInfo(@Nullable CrawlerInfo cxInfo) {
		if (cxInfo != null) {
			cxInfo.restoreThreadLocalStatus();
		}
	}
	public String getCrawlerManagerBeanName() {
		return this.crawlerManagerBeanName;
	}
	public void setCrawlerManagerBeanName(String crawlerManagerBeanName) {
		this.crawlerManagerBeanName = crawlerManagerBeanName;
	}
	public CrawlerManager getCrawlerManager() {
		return this.crawlerManager;
	}
	public void setCrawlerManager(CrawlerManager crawlerManager) {
		this.crawlerManager = crawlerManager;
	}
	public CrawlerAttributeSource getCrawlerAttributeSource() {
		return this.crawlerAttributeSource;
	}
	public void setCrawlerAttributeSource(CrawlerAttributeSource crawlerAttributeSource) {
		this.crawlerAttributeSource = crawlerAttributeSource;
	}
	protected final class CrawlerInfo {
		@Nullable
		private final CrawlerManager crawlerManager;

		@Nullable
		private final CrawlerAttribute crawlerAttribute;

		private final String joinpointIdentification;

		@Nullable
		private CrawlerStatus crawlerStatus;

		@Nullable
		private CrawlerInfo oldCrawlerInfo;
		
		public CrawlerInfo(CrawlerManager crawlerManager, CrawlerAttribute crawlerAttribute,
				String joinpointIdentification) {
			super();
			this.crawlerManager = crawlerManager;
			this.crawlerAttribute = crawlerAttribute;
			this.joinpointIdentification = joinpointIdentification;
		}
		public CrawlerManager getCrawlerManager() {
			Assert.state(this.crawlerManager != null, "No CrawlerManager set");
			return this.crawlerManager;
		}
		public CrawlerAttribute getCrawlerAttribute() {
			return this.crawlerAttribute;
		}
		public String getJoinpointIdentification() {
			return this.joinpointIdentification;
		}
		public void newCrawlerStatus(CrawlerStatus crawlerStatus) {
			this.crawlerStatus = crawlerStatus;
		}
		public CrawlerStatus getCrawlerStatus() {
			return this.crawlerStatus;
		}
		public boolean hasCrawler() {
			return (this.crawlerStatus != null);
		}
		private void bindToThread() {
			this.oldCrawlerInfo = CRAWLERINFO_THREADLOCAL.get();
			CRAWLERINFO_THREADLOCAL.set(this);
		}
		private void restoreThreadLocalStatus() {
			CRAWLERINFO_THREADLOCAL.set(this.oldCrawlerInfo);
		}
		@Override
		public String toString() {
			return (this.crawlerAttribute != null ? this.crawlerAttribute.toString() : "No Crawler");
		}
	}
	@FunctionalInterface
	protected interface InvocationCallback {

		Object proceedWithInvocation() throws Throwable;
	}
}
