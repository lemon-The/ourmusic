package com.lemonthe.ourmusic.audioScraper.dto;

/**
 * AudioDataDTO
 */
public record AudioDataDTO(
		Long id,
		String title,
		String artist,
		String coverURL,
		String trackURL) {}
