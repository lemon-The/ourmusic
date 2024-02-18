package com.lemonthe.ourmusic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lemonthe.ourmusic.audioScraper.AudioDataRepository;
import com.lemonthe.ourmusic.audioScraper.AudioScraperService;
import com.lemonthe.ourmusic.audioScraper.HitmoScraper;

/**
 * OurmusicConfiguration
 */
@Configuration
public class OurmusicConfiguration {

	@Bean
	public AudioScraperService getAudioScraperService(AudioDataRepository audioDataRepo, HitmoScraper scraper) {
		return new AudioScraperService(audioDataRepo, scraper);
	}
	
}
