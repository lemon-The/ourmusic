package com.lemonthe.ourmusic.audioScraper;

import java.util.List;

/**
 * AudioResource
 */
public interface AudioResource {

  List<AudioData> scrapAudio(String searchQuery) throws AudioScrapingException;

	String getResourceName();
} 
