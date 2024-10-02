package com.reyco.crawler;

import org.openqa.selenium.WebDriver;

public class WebDriverHolder {
	
	private WebDriver webDriver;
	
	public WebDriverHolder() {
		
	}
	public WebDriverHolder(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}
	public boolean hasWebDriver() {
		return webDriver!=null;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}
}
