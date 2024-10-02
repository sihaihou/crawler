package com.reyco.crawler.parser.core.parse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;
import com.reyco.crawler.parser.core.locate.CrawlerPageLocater;
import com.reyco.crawler.parser.core.locate.LocateMode;
import com.reyco.crawler.parser.core.match.AfterMatcher;
import com.reyco.crawler.parser.core.match.BeforeMatcher;
import com.reyco.crawler.parser.core.match.DefaultMatcher;
import com.reyco.crawler.parser.core.match.Matcher;
import com.reyco.crawler.parser.core.match.MatcherFactory;
import com.reyco.crawler.parser.core.match.SubstringMatcher;

@Component
public class DefaultCrawlerPageLocateParser implements CrawlerPageLocateParser {
	
	private static final Boolean DEFAULT_TYPE = true;
	
	@Autowired
	private MatcherFactory matcherFactory;
	public DefaultCrawlerPageLocateParser() {
		// TODO Auto-generated constructor stub
	}
	
	public DefaultCrawlerPageLocateParser(MatcherFactory matcherFactory) {
		super();
		this.matcherFactory = matcherFactory;
	}

	@Override
	public boolean support(Object type) {
		if(type instanceof Boolean) {
			Boolean t = (Boolean)type;
			return t==DEFAULT_TYPE;
		}
		return false;
	}
	@Override
	public Enter parse(WebDriver webDriver, CrawlerPageLocater crawlerPageLocater, Boolean isDetail) throws CrawlerPageLocateParseException {
		if(StringUtils.isBlank(crawlerPageLocater.getExpression())) {
			throw new CrawlerPageLocateParseException();
		}
		String key = crawlerPageLocater.getName();
		Matcher matcher = matcherFactory.getMatcher(crawlerPageLocater.getAttribute());
		List<WebElement> webElements = findElement(webDriver, crawlerPageLocater.getMode(), crawlerPageLocater.getExpression(), isDetail);
		List<Object> values = new ArrayList<>(webElements.size());
		for (WebElement webElement : webElements) {
			values.add(getValue(webElement, matcher));
		}
		return new DefaultEnter(key, values);
	}
	/**
	 * 获取List<WebElement>
	 * @param webDriver
	 * @param locateMode
	 * @param expression
	 * @param detail
	 * @return
	 * @throws CrawlerPageLocateParseException
	 */
	private List<WebElement> findElement(WebDriver webDriver, LocateMode locateMode, String expression, Boolean detail)
			throws CrawlerPageLocateParseException {
		if (detail) {
			WebElement webElement = null;
			switch (locateMode) {
			case ID:
				webElement = webDriver.findElement(By.id(expression));
				break;
			case NAME:
				webElement = webDriver.findElement(By.name(expression));
				break;
			case CLASS_NAME:
				webElement = webDriver.findElement(By.className(expression));
				break;
			case TAG_NAME:
				webElement = webDriver.findElement(By.tagName(expression));
				break;
			case LINK_TEXT:
				webElement = webDriver.findElement(By.linkText(expression));
				break;
			case PARTIAL_LINK_TEXT:
				webElement = webDriver.findElement(By.partialLinkText(expression));
				break;
			case XPATH:
				webElement = webDriver.findElement(By.xpath(expression));
				break;
			case CSS_SELECTOR:
				webElement = webDriver.findElement(By.cssSelector(expression));
				break;
			default:
				throw new CrawlerPageLocateParseException();
			}
			List<WebElement> webElements = new ArrayList<>();
			webElements.add(webElement);
			return webElements;
		} else {
			switch (locateMode) {
			case ID:
				return webDriver.findElements(By.id(expression));
			case NAME:
				return webDriver.findElements(By.name(expression));
			case CLASS_NAME:
				return webDriver.findElements(By.className(expression));
			case TAG_NAME:
				return webDriver.findElements(By.tagName(expression));
			case LINK_TEXT:
				return webDriver.findElements(By.linkText(expression));
			case PARTIAL_LINK_TEXT:
				return webDriver.findElements(By.partialLinkText(expression));
			case XPATH:
				return webDriver.findElements(By.xpath(expression));
			case CSS_SELECTOR:
				return webDriver.findElements(By.cssSelector(expression));
			default:
				throw new CrawlerPageLocateParseException();
			}
		}
	}
	protected String getValue(WebElement webElement, Matcher matcher) {
		String attributeName = (String)matcher.getValue(Matcher.ATTRIBUTE_NAME);
		String attributeValue;
		if ("text".equalsIgnoreCase(attributeName)) {
			attributeValue = webElement.getText();
		} else {
			attributeValue = webElement.getAttribute(attributeName);
		}
		if(StringUtils.isBlank(attributeValue)) {
			return attributeValue;
		}
		String type = (String)matcher.getValue(Matcher.TYPE_NAME);
		int startIndex=0;
		int endIndex=attributeValue.length();
		switch (type) {
		case DefaultMatcher.TYPE:
			break;
		case AfterMatcher.TYPE:
			String delimiterAfter = (String)matcher.getValue(Matcher.DELIMITER_NAME);
			if(attributeValue.contains(delimiterAfter)) {
				startIndex = attributeValue.indexOf(delimiterAfter)+delimiterAfter.length();
			}
			break;
		case BeforeMatcher.TYPE:
			String delimiterBefore = (String)matcher.getValue(Matcher.DELIMITER_NAME);
			if(attributeValue.contains(delimiterBefore)) {
				endIndex = attributeValue.indexOf(delimiterBefore);
			}
			break;
		case SubstringMatcher.TYPE:
			startIndex = (int)matcher.getValue(Matcher.START_INDEX);
			Object endIndexValue = matcher.getValue(Matcher.END_INDEX);
			if(endIndexValue!=null) {
				int end = (int)endIndexValue;
				if(end>startIndex) {
					endIndex = end;
				}
			}
			break;
		default:
			break;
		}
		String value = attributeValue.substring(startIndex,endIndex);
		if(StringUtils.isNotBlank(value)) {
			return value.trim();
		}
		return value;
	}

	public MatcherFactory getMatcherFactory() {
		return matcherFactory;
	}
	public void setMatcherFactory(MatcherFactory matcherFactory) {
		this.matcherFactory = matcherFactory;
	}
	
}
