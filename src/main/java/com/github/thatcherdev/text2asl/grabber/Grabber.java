package com.github.thatcherdev.text2asl.grabber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.thatcherdev.text2asl.Text2ASL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Grabber {

    private static String[] letterURLs = new String[] { "sign/A/5820/1", "search/b", "search/c", "search/d", "search/e",
            "search/f", "sign/G/5826/1", "search/h", "sign/I/5828/1", "search/j", "search/k", "sign/L/5831/1",
            "sign/M/5832/1", "search/n", "search/o", "search/p", "search/q", "search/r", "search/s", "sign/T/5839/1",
            "search/u", "search/v", "search/w", "search/x", "search/y", "search/z" };
    private static String baseURL = "https://www.signingsavvy.com/";
    private static String baseCSS = "html body#page_signs.bg div#frame div#main.index div#main.sub div#main_content div#main_content_inner div#main_content_left div.content_module";

    /**
     * Gets an ArrayList of URLs for videos of the ASL signs that make up sentence
     * {@sentence}.
     * <p>
     * Splits sentence {@link sentence} into words. For each word, if there is a
     * single sign/meaning, gets the URL for the video of that sign. If the word has
     * multiple signs/meanings, prompts user to select sign/meaning and gets the URL
     * for the video of that selection. If there are no signs for the word, splits
     * the word into letters and gets a URL for the video of each letter.
     * 
     * @param sentence sentence to get URLs for videos of
     * @return ArrayList<String> URLs for videos of the signs that make up sentence
     *         {@link sentence}
     * @throws IOException
     * @throws InterruptedException
     */
    public static ArrayList<String> getVideoURLsFromSentence(String sentence) throws IOException, InterruptedException {
        ArrayList<String> ret = new ArrayList<String>();

        List<String> words = Arrays.asList(sentence.split(" "));

        for (int k = 0; k < words.size(); k++) {
            String word = words.get(k);
            int indexOfWord = words.indexOf(word);

            if (indexOfWord != k)
                ret.add(Integer.toString(indexOfWord));
            else {
                Document document = Jsoup.connect(baseURL + "search/" + word).get();
                String searchResults = document.selectFirst(baseCSS + " h2").toString();

                if (searchResults.contains("Search Results")) {
                    if (document.selectFirst(baseCSS + " div.search_results") != null) {
                        Elements meaningOptions = document.selectFirst(baseCSS + " div.search_results ul").children();
                        ArrayList<String> meaningOptionsAsText = (ArrayList<String>) meaningOptions.eachText();

                        for (int i = 0; i < meaningOptionsAsText.size(); i++)
                            meaningOptionsAsText.set(i, meaningOptionsAsText.get(i).replace("&quot", "\""));

                        String meaningChoice = Text2ASL.frame
                                .getRadioChoice("Select correct meaning for '" + word + "':", meaningOptionsAsText);
                        ret.add(getVideoURL(baseURL + meaningOptions.get(meaningOptionsAsText.indexOf(meaningChoice))
                                .child(0).attr("href")));
                    } else
                        for (char c : word.toCharArray()) {
                            int letterURLsIndex = (int) c - 65;
                            ret.add(getVideoURL(baseURL + letterURLs[letterURLsIndex]));
                        }
                } else
                    ret.add(getVideoURL(baseURL + "search/" + word));
            }
        }
        return ret;
    }

    /**
     * Gets the direct URL for the video on webpage {@link pageURL}.
     * 
     * @param pageURL webpage with video on it
     * @return String direct URL for video on webpage {@link pageURL}
     * @throws IOException
     */
    private static String getVideoURL(String pageURL) throws IOException {
        Document document = Jsoup.connect(pageURL).get();
        String videoURL = document.selectFirst(baseCSS + " div.sign_module div.signing_body div.videocontent link")
                .attr("href").toString();
        return baseURL + videoURL;
    }
}