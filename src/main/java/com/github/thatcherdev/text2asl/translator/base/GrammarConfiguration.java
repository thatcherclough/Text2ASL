/**
 * The following code was adapted from:
 * Author: Harsh Bhavsar
 * Date: February 24, 2020
 * Availability: https://github.com/harshbits/english-asl-algorithm
 */
package com.github.thatcherdev.text2asl.translator.base;

import java.util.ArrayList;
import java.util.Arrays;

public class GrammarConfiguration {

	public static class BeWords {

		private ArrayList<String> beWords = new ArrayList<String>(
				Arrays.asList(new String[] { "IS", "ARE", "AM", "WAS", "WERE", "BEEN", "BEING", "BE", "WILL", "WOULD",
						"SHOULD", "COULD", "CAN", "HAVE", "HAD", "HAS", "SHALL", "MUST", "MAY", "MIGHT" }));

		public ArrayList<String> getBeWords() {
			return beWords;
		}
	}

	public static class VerbTags {

		private ArrayList<String> verbTags = new ArrayList<String>(
				Arrays.asList(new String[] { "VB", "VBD", "VBG", "VBN", "VBP", "VBZ" }));
		private ArrayList<String> adverbTags = new ArrayList<String>(
				Arrays.asList(new String[] { "RB", "RBR", "RBS" }));

		public ArrayList<String> getVerbTags() {
			return verbTags;
		}

		public ArrayList<String> getAdverbTags() {
			return adverbTags;
		}
	}

	public static class NounTags {

		private ArrayList<String> nounTags = new ArrayList<String>(
				Arrays.asList(new String[] { "NN", "NNS", "NNP", "NNPS" }));
		private ArrayList<String> pluralNounTags = new ArrayList<String>(
				Arrays.asList(new String[] { "NN", "NNS", "NNPS" }));
		private ArrayList<String> adjectiveTags = new ArrayList<String>(
				Arrays.asList(new String[] { "JJ", "JJR", "JJS" }));

		public ArrayList<String> getNounTags() {
			return nounTags;
		}

		public ArrayList<String> getPluralNounTags() {
			return pluralNounTags;
		}

		public ArrayList<String> getAdjectiveTags() {
			return adjectiveTags;
		}
	}

	public static class ValidPOS {

		private ArrayList<String> validPos = new ArrayList<String>(
				Arrays.asList(new String[] { "CC", "CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD", "NN",
						"NNS", "NNP", "NNPS", "PDT", "PRP", "PRP$", "RB", "RBR", "RBS", "RP", "SYM", "TO", "UH", "VB",
						"VBD", "VBG", "VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB" }));

		public ArrayList<String> getValidPos() {
			return validPos;
		}
	}

	public static class TimeWords {

		private ArrayList<String> timeWords = new ArrayList<String>(Arrays.asList(
				new String[] { "YESTERDAY", "MORNING", "AFTERNOON", "EVENING", "NIGHT", "YESTERDAY", "TOMORROW" }));

		public ArrayList<String> getTimeWords() {
			return timeWords;
		}
	}

	public static class NegationWords {

		private ArrayList<String> negationWords = new ArrayList<String>(
				Arrays.asList(new String[] { "NOT", "NONE", "NEVER", "NOBODY", "NOTHING", "NOONE", "N'T" }));

		public ArrayList<String> getNegationWords() {
			return negationWords;
		}
	}
}