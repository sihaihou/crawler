package com.reyco.crawler.core.interceptor;

public class DefaultCrawlerAttribute implements CrawlerAttribute {
	
	private String name;
	
	private String value;
	
	private String qualifier;
	
	public DefaultCrawlerAttribute() {
	}
	public DefaultCrawlerAttribute(String name) {
		super();
		this.name = name;
	}
	@Override
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getQualifier() {
		return this.qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
}
