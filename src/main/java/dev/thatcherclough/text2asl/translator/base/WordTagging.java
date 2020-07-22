/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package dev.thatcherclough.text2asl.translator.base;

public class WordTagging {

	private String word;
	private String tag;

	public WordTagging() {
		super();
	}

	public WordTagging(String word, String tag) {
		this.word = word;
		this.tag = tag;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}