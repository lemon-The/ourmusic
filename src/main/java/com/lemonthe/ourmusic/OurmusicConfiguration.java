package com.lemonthe.ourmusic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lemonthe.ourmusic.audioScraper.AudioDataRepository;
import com.lemonthe.ourmusic.audioScraper.ScraperService;
import com.lemonthe.ourmusic.audioScraper.HitmoScraper;

/**
 * OurmusicConfiguration
 */
@Configuration
public class OurmusicConfiguration {

	@Bean
	public ScraperService getAudioScraperService(
			AudioDataRepository audioDataRepo,
			HitmoScraper hitmoScraper) {
		return new ScraperService(audioDataRepo, List.of(hitmoScraper));
	}
	
}
