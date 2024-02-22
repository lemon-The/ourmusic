package com.lemonthe.ourmusic.scraper;

import java.util.List;

/**
 * AudioResource
 */
public interface Scraper {

  List<AudioData> scrapAudio(String searchQuery) throws ScrapingException;

	String getWebsiteName();
} 
