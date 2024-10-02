package com.reyco.crawler.core.interceptor;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.reyco.crawler.core.parser.CrawlerAnnotationParser;
import com.reyco.crawler.core.parser.DefaultCrawlerAnnotationParser;

public class AnnotationCrawlerAttributeSource extends AbstractCrawlerAttributeSource{
	
	private final boolean publicMethodsOnly;

	private final Set<CrawlerAnnotationParser> annotationParsers;
	
	public AnnotationCrawlerAttributeSource() {
		this(true);
	}

	public AnnotationCrawlerAttributeSource(boolean publicMethodsOnly) {
		this.publicMethodsOnly = publicMethodsOnly;
		this.annotationParsers = Collections.singleton(new DefaultCrawlerAnnotationParser());
	}

	public AnnotationCrawlerAttributeSource(CrawlerAnnotationParser annotationParser) {
		this.publicMethodsOnly = true;
		Assert.notNull(annotationParser, "ResourceAnnotationParser must not be null");
		this.annotationParsers = Collections.singleton(annotationParser);
	}
	public AnnotationCrawlerAttributeSource(CrawlerAnnotationParser... annotationParsers) {
		this.publicMethodsOnly = true;
		Assert.notEmpty(annotationParsers, "At least one ResourceAnnotationParser needs to be specified");
		this.annotationParsers = new LinkedHashSet<>(Arrays.asList(annotationParsers));
	}
	public AnnotationCrawlerAttributeSource(Set<CrawlerAnnotationParser> annotationParsers) {
		this.publicMethodsOnly = true;
		Assert.notEmpty(annotationParsers, "At least one ResourceAnnotationParser needs to be specified");
		this.annotationParsers = annotationParsers;
	}
	@Override
	protected CrawlerAttribute findCrawlerAttribute(Class<?> clazz) {
		return determineResourceAttribute(clazz);
	}

	@Override
	protected CrawlerAttribute findCrawlerAttribute(Method method) {
		return determineResourceAttribute(method);
	}
	@Nullable
	protected CrawlerAttribute determineResourceAttribute(AnnotatedElement element) {
		for (CrawlerAnnotationParser annotationParser : this.annotationParsers) {
			CrawlerAttribute attr = annotationParser.parseCrawlerAnnotation(element);
			if (attr != null) {
				return attr;
			}
		}
		return null;
	}
	@Override
	protected boolean allowPublicMethodsOnly() {
		return this.publicMethodsOnly;
	}

}
