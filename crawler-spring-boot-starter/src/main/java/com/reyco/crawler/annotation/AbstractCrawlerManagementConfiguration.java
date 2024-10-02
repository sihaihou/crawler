package com.reyco.crawler.annotation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import com.reyco.crawler.CrawlerManagementConfigurer;
import com.reyco.crawler.CrawlerManager;

public class AbstractCrawlerManagementConfiguration implements ImportAware {
	
	@Nullable
	protected AnnotationAttributes enableCx;
	
	@Nullable
	protected CrawlerManager cxManager;
	
	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		this.enableCx = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableCrawlerManagement.class.getName(), false));
		if (this.enableCx == null) {
			throw new IllegalArgumentException("@EnableCrawlerManagement is not present on importing class " + importMetadata.getClassName());
		}
	}
	
	@Autowired(required = false)
	void setConfigurers(Collection<CrawlerManagementConfigurer> configurers) {
		if (CollectionUtils.isEmpty(configurers)) {
			return;
		}
		if (configurers.size() > 1) {
			throw new IllegalStateException("Only one CrawlerManagementConfigurer may exist");
		}
		CrawlerManagementConfigurer configurer = configurers.iterator().next();
		this.cxManager = configurer.annotationDrivenCrawlerManager();
	}

}
