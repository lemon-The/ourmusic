package com.lemonthe.ourmusic.scraper.dto;

/**
 * AudioDataDTO
 */
public record AudioDataDTO(
		Long id,
		String title,
		String artist,
		String coverURL,
		String trackURL) {}
