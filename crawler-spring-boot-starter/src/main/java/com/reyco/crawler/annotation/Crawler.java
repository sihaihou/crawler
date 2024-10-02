package com.reyco.crawler.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Crawler {
	
	@AliasFor("crawlerManager")
	String value() default "";
	
	@AliasFor("value")
	String crawlerManager() default "";
	
}
