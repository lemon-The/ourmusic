package com.lemonthe.ourmusic.audioScraper;

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

import lombok.Setter;

/**
 * HitmoScraper
 */
public class HitmoScraper implements AudioScraper {

  @Setter
  @Value("$(scraper.hitmo.url)")
  private String hitmoURL;

  @Override
  public List<AudioData> scrapAudio(String searchQuery)
			throws AudioScrapingException {
    List<AudioData> data = new LinkedList<>();
		int audioDataIndex = 0;

		while (audioDataIndex < 10) {
      Document htmlPage = fetchHTMLPage(searchQuery, audioDataIndex);

      // html code blocks containing audio data
      Elements htmlBlocks = htmlPage.select("li[data-musid]");

      if (htmlBlocks.isEmpty()) {
        return data;
      }

      for (Element block : htmlBlocks) {
        AudioData fetchedAudio = fetchDataFromHTMLBlock(block, searchQuery);
        data.add(fetchedAudio);
        audioDataIndex++;
      }

    }
		return data;
  }

  private AudioData fetchDataFromHTMLBlock(Element block, String searchQuery) {
    String title = block.select("div.track__title").text();
    String artist = block.select("div.track__desc").text();
    String coverURL = fetchCoverLocation(block
        .select("div[style^=background-image:]")
        .attr("style"));
    String audioURL = fetchAudioLocation(block
        .select("a[data-nopjax]")
        .attr("href"));

    return new AudioData(title, artist, coverURL, audioURL, searchQuery);
  }

  private Document fetchHTMLPage(String query, int start)
      throws AudioScrapingException {
    try {
      return Jsoup.connect(hitmoURL + "search/start/" + start)
          .userAgent("Mozilla")
          .timeout(3000)
          .data("q", query).get();
    } catch (MalformedURLException
        | HttpStatusException
        | UnsupportedMimeTypeException
        | SocketTimeoutException e) {
      throw new AudioScrapingException(e);
    } catch (IOException e) {
      throw new AudioScrapingException(e);
    }
  }

  private String fetchCoverLocation(String coverString) {
    int startIndex = coverString.indexOf('(') + 3;
    int endIndex = coverString.lastIndexOf(')') - 1;
    return coverString.substring(startIndex, endIndex);
  }

  private String fetchAudioLocation(String audioString) {
    int startIndex = audioString.indexOf('/', hitmoURL.length() - 1) + 1;
    int endIndex = audioString.length();
    return audioString.substring(startIndex, endIndex);
  }

}
