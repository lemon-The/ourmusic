package com.lemonthe.ourmusic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lemonthe.ourmusic.scraper.AudioDataRepository;
import com.lemonthe.ourmusic.scraper.HitmoScraper;
import com.lemonthe.ourmusic.scraper.ScraperService;

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
