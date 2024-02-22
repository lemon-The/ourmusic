package com.lemonthe.ourmusic.audioScraper.dto;

import com.lemonthe.ourmusic.audioScraper.AudioData;

/**
 * AudioDataMapper
 */
public class AudioDataMapper {

	public static AudioDataDTO asAudioDataDTO(AudioData audioData) {
		return new AudioDataDTO(audioData.getId(), audioData.getTitle(),
				audioData.getArtist(), audioData.getCoverURL(), audioData.getTrackURL());
	}
}
