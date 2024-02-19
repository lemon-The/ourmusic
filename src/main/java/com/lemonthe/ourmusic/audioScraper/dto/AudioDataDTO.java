package com.lemonthe.ourmusic.audioScraper.dto;

import com.lemonthe.ourmusic.audioScraper.AudioSource;

/**
 * AudioDataDTO
 */
public record AudioDataDTO(
		Long id,
		String title,
		String artist,
		String coverURL,
		String trackURL,
		AudioSource source) {}
