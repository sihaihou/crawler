package com.reyco.crawler.parser.core.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.reyco.crawler.parser.core.exception.CrawlerPageLocateParseException;

@Component
public class DefaultMatcherFactory implements MatcherFactory{
	
	@Override
	public Matcher getMatcher(String s) throws CrawlerPageLocateParseException{
		DefaultMatcher defaultMatcher = null;
		try {
			for (Matcher matcher : getMatchers()) {
				if(matcher.matches(s)) {
					return matcher;
				}
			}
			defaultMatcher = new DefaultMatcher();
			defaultMatcher.matches(s);
		} catch (Exception e) {
			throw new CrawlerPageLocateParseException(e.getMessage());
		}
		return defaultMatcher;
	}
	
	private static List<Matcher> getMatchers(){
		List<Matcher> matchers = new ArrayList<Matcher>();
		matchers = new ArrayList<Matcher>();
		matchers.add(new AfterMatcher());
		matchers.add(new BeforeMatcher());
		matchers.add(new SubstringMatcher());
		matchers.add(new DefaultMatcher());
		return matchers;
	}
	
}
