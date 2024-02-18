package com.lemonthe.ourmusic.audioScraper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;

/**
 * AudioScraperService
 */
@Service
public class AudioScraperService {

	private AudioDataRepository audioDataRepo;
	private AudioScraper audioScraper;

	public AudioScraperService(AudioDataRepository audioDataRepo,
			AudioScraper audioScraper) {
		this.audioDataRepo = audioDataRepo;
		this.audioScraper = audioScraper;
	}

	// @Transactional
	// public List<AudioData> getAudioData() {
	// return audioDataRepo.findAll();
	// }

	@Transactional
	public List<AudioData> getAudioData(String query)
			throws AudioScrapingException {
		return fetchAudioDataByQuery(query);
	}

	private List<AudioData> fetchAudioDataByQuery(String query)
			throws AudioScrapingException {
		List<AudioData> data = new ArrayList<>();

		audioDataRepo.findAllBySearchQuery(query)
				.forEach(i -> data.add(i));

		if (data.isEmpty()) {
			saveAudioData(query).forEach(i -> data.add(i));
		}
		return data;
	}

	private List<AudioData> saveAudioData(String query)
			throws AudioScrapingException {
		List<AudioData> data = audioScraper.scrapAudio(query);
		if (!data.isEmpty()) {
			for (AudioData d : data) {
				audioDataRepo.save(d);
			}
		}
		return data;
	}


}
