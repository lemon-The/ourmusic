package com.lemonthe.ourmusic.scraper.dto;

import com.lemonthe.ourmusic.scraper.AudioData;

/**
 * AudioDataMapper
 */
public class AudioDataMapper {

	public static AudioDataDTO asAudioDataDTO(AudioData audioData) {
		return new AudioDataDTO(audioData.getId(), audioData.getTitle(),
				audioData.getArtist(), audioData.getCoverURL(), audioData.getTrackURL());
	}
}
