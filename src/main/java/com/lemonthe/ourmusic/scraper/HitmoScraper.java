package com.lemonthe.ourmusic.scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Setter;

/**
 * HitmoScraper
 */
@Component
public class HitmoScraper implements Scraper {

  @Setter
  @Value("${audio-resource.hitmo.url:https://rus.hitmotop.com/}")
  private String hitmoURL;

	private final String hitmoName = "hitmo";

	@Override
	public String getWebsiteName() {
		return hitmoName;
	}

  @Override
  public List<AudioData> scrapAudio(String searchQuery)
			throws ScrapingException {
		List<AudioData> data = new LinkedList<>();

		//data of first audio on current page and ...
		AudioData audioCurrent = null;
		//... on previous page
		AudioData audioPrevious = null;
		int audioIndex = 0;
		
		/* If audioIndex is greater than total amount of audio for this searchQuery,
		 * hitmo returns the last available audio.
		 * Parse audio until audioCurrent and audioPrevious are equal */
		while (true) {
			Document htmlPage = fetchHTMLPage(searchQuery, audioIndex);
			
			//html code blocks containing audio data
			Elements htmlBlocks = htmlPage.select("li[data-musid]");
			if (htmlBlocks.isEmpty()){
				return data;
			}

			audioCurrent = parseDataFromHTMLBlock(htmlBlocks.get(0), searchQuery);
			if (audioPrevious != null
					&& audioPrevious.equals(audioCurrent)) {
				return data;
			}

			for (Element block : htmlBlocks) {
				AudioData fetchedAudioData = parseDataFromHTMLBlock(block, searchQuery);
				data.add(fetchedAudioData);
				audioIndex++;
			}
			audioPrevious = audioCurrent;
		}
	}

	private Document fetchHTMLPage(String searchQuery, int start)
			throws ScrapingException {
		Document htmlPage;
		try {
			htmlPage = 
				Jsoup.connect(hitmoURL + "search/start/" + start)
					.userAgent("Mozilla")
					.data("q", searchQuery) 
					.get();
		} catch (MalformedURLException
				| HttpStatusException
				| UnsupportedMimeTypeException
				| SocketTimeoutException e) {
			throw new ScrapingException(e);
		} catch (IOException e) {
			throw new ScrapingException(e);
		}
		return htmlPage;
	}

	private AudioData parseDataFromHTMLBlock(Element block, String searchQuery) {
		String title = block.select("div.track__title").text();
		String artist = block.select("div.track__desc").text();
		String coverURL =
			//fetchCoverLocation(block
			//		.select("div[style^=background-image:]")
			//		.attr("style"));
			parseCoverURL(block
					.select("div.track__img")
					.attr("style"));
		String audioURL = block.select("a[data-nopjax]").attr("href");
					//.select("a.track__download-btn")

		return new AudioData(title, artist, coverURL, audioURL, searchQuery,
				hitmoName);
	}

	private String parseCoverURL(String coverString) {
		int beginIndex = coverString.indexOf('\'')+1;
		int endIndex = coverString.lastIndexOf('\'');
		return coverString.substring(beginIndex, endIndex);
	}

}
