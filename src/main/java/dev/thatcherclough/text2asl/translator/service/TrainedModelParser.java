/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package dev.thatcherclough.text2asl.translator.service;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;

import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.Rule;
import org.languagetool.rules.spelling.SpellingCheckRule;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.dictionary.MorphologicalProcessor;

public class TrainedModelParser {

	/**
	 * Uses stanford's lexicalized parser to parse sentence {@link sentence}.
	 * 
	 * @param sentence sentence to parse
	 * @return Tree lexicalized parser tree
	 */
	public Tree getLexicalizedParserTree(String sentence) {
		try {
			LogManager.getLogManager().reset();
			String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
			TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(),
					"invertible=true");
			LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);
			Tokenizer<CoreLabel> tokenizer = tokenizerFactory.getTokenizer(new StringReader(sentence));
			List<CoreLabel> tokens = tokenizer.tokenize();
			return parser.apply(tokens);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets JLanguage tool for checking grammar of a sentence.
	 * 
	 * @return JLanguageTool
	 */
	public JLanguageTool getJLanguageTool() {
		try {
			JLanguageTool ret = new JLanguageTool(new AmericanEnglish());

			// exclude words from spell checking
			for (Rule rule : ret.getAllActiveRules())
				if (rule instanceof SpellingCheckRule) {
					List<String> wordsToIgnore = Arrays.asList("");
					((SpellingCheckRule) rule).addIgnoreTokens(wordsToIgnore);
				}

			// ignore phrases from spell checking
			for (Rule rule : ret.getAllActiveRules())
				if (rule instanceof SpellingCheckRule)
					((SpellingCheckRule) rule).acceptPhrases(Arrays.asList(""));
			return ret;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets MorphologicalProcessor for verb manipulation.
	 * 
	 * @return MorphologicalProcessor
	 */
	public MorphologicalProcessor getMorphologicalProcessor() {
		try {
			Dictionary wordnet = Dictionary.getDefaultResourceInstance();
			return wordnet.getMorphologicalProcessor();
		} catch (Exception e) {
			return null;
		}
	}
}