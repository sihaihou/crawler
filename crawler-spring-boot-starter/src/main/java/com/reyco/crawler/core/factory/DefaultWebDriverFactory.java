package com.reyco.crawler.core.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.springframework.beans.factory.InitializingBean;

import com.reyco.crawler.properties.CrawlerProperties;

public class DefaultWebDriverFactory implements WebDriverFactory,InitializingBean {
	
	private CrawlerProperties crawlerProperties;
	
	public DefaultWebDriverFactory() {
	}
	
	public DefaultWebDriverFactory(CrawlerProperties crawlerProperties) {
		super();
		this.crawlerProperties = crawlerProperties;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		if (crawlerProperties == null) {
			crawlerProperties = new CrawlerProperties();
		}
		System.setProperty("webdriver." + crawlerProperties.getName() + ".driver", crawlerProperties.getDriver());
	}
	@Override
	public WebDriver createWebDriver() {
		WebDriver webDriver = null;
		switch (crawlerProperties.getName()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			webDriver = new ChromeDriver(chromeOptions);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			webDriver = new EdgeDriver(edgeOptions);
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			webDriver = new FirefoxDriver(firefoxOptions);
			break;
		case "opera":
			OperaOptions operaOptions = new OperaOptions();
			webDriver = new OperaDriver(operaOptions);
			break;
		case "safari":
			SafariOptions safariOptions = new SafariOptions();
			webDriver = new SafariDriver(safariOptions);
			break;
		default:
			ChromeOptions defaultOptions = new ChromeOptions();
			webDriver = new ChromeDriver(defaultOptions);
			break;
		}
		return webDriver;
	}
	public void setCrawlerProperties(CrawlerProperties crawlerProperties) {
		this.crawlerProperties = crawlerProperties;
	}
	
	public CrawlerProperties getCrawlerProperties() {
		return crawlerProperties;
	}
}
