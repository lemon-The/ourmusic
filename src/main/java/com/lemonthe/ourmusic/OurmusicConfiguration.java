package com.lemonthe.ourmusic;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lemonthe.ourmusic.audioScraper.AudioDataRepository;
import com.lemonthe.ourmusic.audioScraper.AudioScraperService;
import com.lemonthe.ourmusic.audioScraper.HitmoResource;

/**
 * OurmusicConfiguration
 */
@Configuration
public class OurmusicConfiguration {

	@Bean
	public AudioScraperService getAudioScraperService(
			AudioDataRepository audioDataRepo,
			HitmoResource hitmoScraper) {
		return new AudioScraperService(audioDataRepo, List.of(hitmoScraper));
	}
	
}
