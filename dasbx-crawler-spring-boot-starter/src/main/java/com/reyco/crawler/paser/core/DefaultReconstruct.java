package com.reyco.crawler.paser.core;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultReconstruct implements Reconstruct {
	
	private static SpelExpressionParser parser = new SpelExpressionParser();

	private static TemplateParserContext parserContext = new TemplateParserContext();
	
	@Override
	public String reconstructUrl(String crawlerUrl, Map<String, Object> param) {
		if (StringUtils.isBlank(crawlerUrl)) {
			return "";
		}
		Expression expression = parser.parseExpression(crawlerUrl, parserContext);
		StandardEvaluationContext evaluationContext = new StandardEvaluationContext(param);
		return expression.getValue(evaluationContext, String.class);
	}

}
