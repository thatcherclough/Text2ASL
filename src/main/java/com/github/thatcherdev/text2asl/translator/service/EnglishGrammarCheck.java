/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package com.github.thatcherdev.text2asl.translator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;

import com.github.thatcherdev.text2asl.translator.base.EnglishGrammarCheckResponse;

public class EnglishGrammarCheck {

	private TrainedModelParser trainedModelParser = new TrainedModelParser();

	/**
	 * Checks the grammar of sentence {@link sentence}.
	 * 
	 * @param sentence sentence to check grammar of
	 * @return EnglishGrammarCheckResponse
	 * @throws Exception
	 */
	public EnglishGrammarCheckResponse checkGrammar(String sentence) throws Exception {
		EnglishGrammarCheckResponse ret = new EnglishGrammarCheckResponse();

		JLanguageTool langTool = trainedModelParser.getJLanguageTool();
		if (langTool != null) {
			Map<String, String> suggestions = new HashMap<>();
			List<RuleMatch> matches = new ArrayList<>();
			matches = langTool.check(sentence);
			StringBuffer sb = new StringBuffer(sentence);
			int difference = 0;
			for (RuleMatch match : matches) {
				suggestions.put(match.getFromPos() + "-" + match.getToPos(), match.getMessage());
				sb.replace(match.getFromPos() + difference, match.getToPos() + difference,
						match.getSuggestedReplacements().get(0));
				difference = difference + match.getSuggestedReplacements().get(0).length()
						- (match.getToPos() - match.getFromPos());
			}
			ret.setSentence(sb.toString());
			ret.setSuggestions(suggestions);
		} else
			throw new Exception("JLanguageTool is not defined");
		return ret;
	}
}