package com.reyco.crawler.parser.core.parse;

import java.util.List;

public class DefaultEnter implements Enter {
	private String key;
	private List<Object> value;
	public DefaultEnter() {
	}
	public DefaultEnter(String key, List<Object> value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<Object> getValue() {
		return value;
	}
	public void setValue(List<Object> value) {
		this.value = value;
	}
}
