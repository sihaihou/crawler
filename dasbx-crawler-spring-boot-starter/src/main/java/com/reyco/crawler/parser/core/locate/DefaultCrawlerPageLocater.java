package com.reyco.crawler.parser.core.locate;

public class DefaultCrawlerPageLocater implements CrawlerPageLocater{
	private String name;
	private LocateMode mode = LocateMode.XPATH;
	private String expression;
	private String attribute;
	private Object type = true;
	@Override
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public LocateMode getMode() {
		return this.mode;
	}
	public void setMode(LocateMode mode) {
		this.mode = mode;
	}
	@Override
	public String getExpression() {
		return this.expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	@Override
	public String getAttribute() {
		return this.attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
}
