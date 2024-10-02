package com.reyco.crawler.annotation;

import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CrawlerManagementCofnigurationSeletor implements ImportSelector  {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { 
				AutoProxyRegistrar.class.getName(), 
				ProxyCrawlerManagementConfiguration.class.getName(),
			};
	}

}
