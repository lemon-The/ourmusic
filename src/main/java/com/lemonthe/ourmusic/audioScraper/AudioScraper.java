package com.lemonthe.ourmusic.audioScraper;

import java.util.List;

/**
 * AudioScraper
 */
public interface AudioScraper {

  List<AudioData> scrapAudio(String searchQuery) throws AudioScrapingException;
} 
