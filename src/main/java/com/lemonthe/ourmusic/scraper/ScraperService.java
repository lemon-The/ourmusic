package com.lemonthe.ourmusic.scraper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
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
	}

	@Transactional
	public List<AudioData> getAudioData(String searchQuery, String websiteName, boolean reloadRequired, Pageable pageable)
			throws ScrapingException {
		if (reloadRequired || !audioDataRepo.existsByWebsiteName(websiteName)) {
			audioDataRepo.deleteAllByWebsiteName(websiteName);
			saveAudioData(scrapAudioData(searchQuery, websiteName));
		}
		return audioDataRepo
				.findBySearchQueryAndWebsiteName(searchQuery, websiteName, pageable);
	}

	private List<AudioData> scrapAudioData(String searchQuery, String websiteName) 
			throws ScrapingException {
		Scraper scraperSelected = scrapers
				.stream()
				.filter(scraper -> scraper.getWebsiteName().equals(websiteName))
				.findAny()
				.orElseThrow(() -> new ScrapingException("Scraper for " + websiteName + " doesn't exist"));
		return scraperSelected.scrapAudio(searchQuery);
	}

	private void saveAudioData(List<AudioData> data) {
		if (!data.isEmpty()) {
			audioDataRepo.saveAll(data);
		}
	}
}
