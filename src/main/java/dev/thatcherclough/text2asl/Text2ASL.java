package dev.thatcherclough.text2asl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import dev.thatcherclough.text2asl.grabber.Grabber;
import dev.thatcherclough.text2asl.translator.base.ASLResponse;
import dev.thatcherclough.text2asl.translator.service.ASLConversionService;
import com.google.common.io.Files;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Text2ASL {

	public static Translator frame = null;

	/**
	 * Starts Text2ASL.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
			frame = new Translator("Text2ASL");
			String sentence = frame.getInput("Enter sentence to be translated into ASL:", 30);

			frame.initiateProgressPanel();

			frame.addToProgressPanel("Converting to ASL grammar...", 20, 0);
			ASLConversionService alsConversionService = new ASLConversionService();
			ASLResponse result = alsConversionService.getASLSentence(sentence);
			frame.addToProgressPanel("Done.", 405, 0);

			frame.addToProgressPanel("Getting video URLs...", 20, 35);
			ArrayList<String> URLs = Grabber.getVideoURLsFromSentence(result.getSentence());
			frame.addToProgressPanel("Done.", 405, 35);

			frame.addToProgressPanel("Downloading videos...", 20, 70);
			String[] videos = downloadVideosAndCreateVideoSequence(URLs);
			frame.addToProgressPanel("Done.", 405, 70);

			frame.playVideos(videos);
		} catch (Exception e) {
			error(e);
		}
	}

	/**
	 * Downloads the videos with URLs {@link URLs} to a temp directory and returns
	 * the order in which these videos should be played.
	 * 
	 * @param URLs video URLs
	 * @return String[] order in which videos should be played
	 * @throws IOException
	 */
	private static String[] downloadVideosAndCreateVideoSequence(ArrayList<String> URLs) throws IOException {
		String[] videos = new String[URLs.size()];
		File tempDir = Files.createTempDir();

		for (int k = 0; k < URLs.size(); k++) {
			String URL = URLs.get(k);

			if (!URL.startsWith("https"))
				videos[k] = tempDir + File.separator + URL + ".mp4";
			else {
				ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(URL).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(tempDir + File.separator + k + ".mp4");
				fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
				readableByteChannel.close();
				fileOutputStream.close();
				videos[k] = tempDir + File.separator + k + ".mp4";
			}
		}
		return videos;
	}

	/**
	 * Clears frame and displays Exception {@link e}'s message. Prints stack trace
	 * of {@link e}.
	 * 
	 * @param e Exception
	 */
	public static void error(Exception e) {
		frame.clearAndDisplay("An error has occurred: " + e.getMessage());
		e.printStackTrace();
	}
}