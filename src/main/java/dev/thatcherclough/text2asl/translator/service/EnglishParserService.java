/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package dev.thatcherclough.text2asl.translator.service;

import java.util.ArrayList;
import java.util.List;

import dev.thatcherclough.text2asl.translator.base.EnglishTagResponse;
import dev.thatcherclough.text2asl.translator.base.EnglishGrammarCheckResponse;
import dev.thatcherclough.text2asl.translator.base.WordTagging;

import edu.stanford.nlp.trees.Tree;

public class EnglishParserService {

	private TrainedModelParser trainedModelParser = new TrainedModelParser();
	private EnglishGrammarCheck englishGrammarCheck = new EnglishGrammarCheck();

	/**
	 * Parses sentence {@link sentence} into words.
	 * 
	 * @param sentence     sentence to parse into words
	 * @param grammarCheck if the grammar of {@link sentence} should be checked
	 * @return EnglishTagResponse
	 * @throws Exception
	 */
	public EnglishTagResponse getParsedSentence(String sentence, boolean grammarCheck) throws Exception {
		EnglishTagResponse ret = new EnglishTagResponse();
		ret.setInputSentence(sentence);

		if (grammarCheck) {
			EnglishGrammarCheckResponse checkSentence = englishGrammarCheck.checkGrammar(sentence);
			sentence = checkSentence.getSentence();
			ret.setGrammarCorrected(sentence);
			ret.setEnglishGrammarCheckResponse(checkSentence);
		} else {
			ret.setEnglishGrammarCheckResponse(new EnglishGrammarCheckResponse());
			ret.setGrammarCorrected("Grammar Check = FALSE");
		}

		Tree tree = trainedModelParser.getLexicalizedParserTree(sentence);
		if (tree != null) {
			List<WordTagging> tagWords = new ArrayList<>();
			List<Tree> leaves = tree.getLeaves();
			for (Tree leaf : leaves) {
				Tree parent = leaf.parent(tree);
				tagWords.add(new WordTagging(leaf.label().value(), parent.label().value()));
			}
			ret.setTagWords(tagWords);
		} else
			throw new Exception("Parsed Tree is Empty");
		return ret;
	}
}