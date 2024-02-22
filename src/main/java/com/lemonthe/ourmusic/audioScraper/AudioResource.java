package com.lemonthe.ourmusic.audioScraper;

import java.util.List;

/**
 * AudioScraper
 */
public interface AudioResource {

  List<AudioData> scrapAudio(String searchQuery) throws AudioScrapingException;

	String getResourceName();
} 
