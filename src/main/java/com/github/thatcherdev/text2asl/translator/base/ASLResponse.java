/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package com.github.thatcherdev.text2asl.translator.base;

public class ASLResponse {

	private String sentence;
	private EnglishTagResponse englishTagResponse;
	private EnglishGrammarCheckResponse englishGrammarCheckResponse;

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public EnglishTagResponse getEnglishTagResponse() {
		return englishTagResponse;
	}

	public void setEnglishTagResponse(EnglishTagResponse englishTagResponse) {
		this.englishTagResponse = englishTagResponse;
	}

	public EnglishGrammarCheckResponse getEnglishGrammarCheckResponse() {
		return englishGrammarCheckResponse;
	}

	public void setEnglishGrammarCheckResponse(EnglishGrammarCheckResponse englishGrammarCheckResponse) {
		this.englishGrammarCheckResponse = englishGrammarCheckResponse;
	}
}