package com.lemonthe.ourmusic.audioScraper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;

/**
 * AudioScraperService
 */
@Service
public class AudioScraperService {

	private AudioDataRepository audioDataRepo;
	private List<AudioResource> audioResources;

	public AudioScraperService(AudioDataRepository audioDataRepo,
			List<AudioResource> audioScraper) {
		this.audioDataRepo = audioDataRepo;
		this.audioResources = audioScraper;
	}

	// @Transactional
	// public List<AudioData> getAudioData() {
	// return audioDataRepo.findAll();
	// }

	@Transactional
	public List<AudioData> getAudioData(String query, String resourceName)
			throws AudioScrapingException {
		List<AudioData> data = audioDataRepo
				.findBySearchQueryAndSource(query, resourceName);

		if (data.isEmpty()) {
			AudioResource currentAudioResource = audioResources
					.stream()
					.filter(res -> res.getResourceName().equals(resourceName))
					.findAny()
					.orElseThrow(() -> new AudioScrapingException("Incorrect resource name"));

			data = saveAudioData(query, currentAudioResource);
		}
		return data;
	}

	private List<AudioData> saveAudioData(String query, AudioResource resource)
			throws AudioScrapingException {

		List<AudioData> data = resource.scrapAudio(query);
		if (!data.isEmpty()) {
			for (AudioData d : data) {
				audioDataRepo.save(d);
			}
		}
		return data;
	}


}
