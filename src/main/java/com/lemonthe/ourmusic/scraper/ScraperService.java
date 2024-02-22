package com.lemonthe.ourmusic.scraper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AudioScraperService
 */
@Service
public class ScraperService {

	private AudioDataRepository audioDataRepo;
	private List<Scraper> scrapers;

	public ScraperService(AudioDataRepository audioDataRepo,
			List<Scraper> scrapers) {
		this.audioDataRepo = audioDataRepo;
		this.scrapers = scrapers;

		Set<String> unicResources = new HashSet<>();
		String nonunicResources = this.scrapers.stream()
			.filter(scraper -> !unicResources.add(scraper.getWebsiteName()))
			.map(res -> "\"" + res.getWebsiteName() + "\"")
			.collect(Collectors.joining(", "));
		if (nonunicResources != null && !nonunicResources.isBlank()) {
			throw new WebsiteNameIntersectionException("There are several " + nonunicResources + " resources");
		}

		//String nonunicResources = audioScraper.stream()
		//	.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		//	.entrySet()
		//	.stream()
		//	.filter(e -> e.getValue() > 1)
		//	.map(e -> e.getKey().getResourceName())
		//	.collect(Collectors.joining(", "));
	}

	@Transactional
	public List<AudioData> getAudioData(String query, String websiteName, boolean reloadRequired)
			throws ScrapingException {
		List<AudioData> data = audioDataRepo
				.findBySearchQueryAndResource(query, websiteName);

		if (data.isEmpty() || reloadRequired) {
			Scraper scraperSelected = scrapers
					.stream()
					.filter(scraper -> scraper.getWebsiteName().equals(websiteName))
					.findAny()
					.orElseThrow(() -> new ScrapingException("Scraper for " + websiteName + " doesn't exist"));
			data = loadAudioData(query, scraperSelected);
		}
		return data;
	}

	private List<AudioData> loadAudioData(String query, Scraper scraper)
			throws ScrapingException {
		List<AudioData> data = scraper.scrapAudio(query);
		if (!data.isEmpty()) {
			for (AudioData audio : data) {
				audioDataRepo.save(audio);
			}
		}
		// TODO Refactor this like
		//data.stream().forEach(d -> audioDataRepo.save(d));
		return data;
	}


}
