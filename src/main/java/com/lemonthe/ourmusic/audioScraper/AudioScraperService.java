package com.lemonthe.ourmusic.audioScraper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

		Set<String> unicResources = new HashSet<>();
		String nonunicResources = audioScraper.stream()
			.filter(res -> !unicResources.add(res.getResourceName()))
			.map(res -> "\"" + res.getResourceName() + "\"")
			.collect(Collectors.joining(", "));
		if (nonunicResources != null && !nonunicResources.isBlank()) {
			throw new AudioResourceNameIntersectionException("There are several " + nonunicResources + " resources");
		}

		//String nonunicResources = audioScraper.stream()
		//	.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		//	.entrySet()
		//	.stream()
		//	.filter(e -> e.getValue() > 1)
		//	.map(e -> e.getKey().getResourceName())
		//	.collect(Collectors.joining(", "));
	}

	// @Transactional
	// public List<AudioData> getAudioData() {
	// return audioDataRepo.findAll();
	// }

	@Transactional
	public List<AudioData> getAudioData(String query, String resourceName, boolean reloadRequired)
			throws AudioScrapingException {
		List<AudioData> data = audioDataRepo
				.findBySearchQueryAndResource(query, resourceName);

		if (data.isEmpty() || reloadRequired) {
			AudioResource currentAudioResource = audioResources
					.stream()
					.filter(res -> res.getResourceName().equals(resourceName))
					.findAny()
					.orElseThrow(() -> new AudioScrapingException("Incorrect resource name"));
			data = loadAudioData(query, currentAudioResource);
		}
		return data;
	}

	private List<AudioData> loadAudioData(String query, AudioResource resource)
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
