package com.lemonthe.ourmusic.scraper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lemonthe.ourmusic.scraper.dto.AudioDataDTO;
import com.lemonthe.ourmusic.scraper.dto.AudioDataMapper;

/**
 * AudioScraperController
 */
@RestController
@RequestMapping("/scraper")
public class ScraperController {

	@Autowired
	private ScraperService scraperService;

	@GetMapping
	public ResponseEntity<List<AudioDataDTO>> getAudioData(
			@RequestParam("q") String query,
			@RequestParam(value = "r", defaultValue = "false") boolean reloadRequired,
			@RequestParam("src") String websiteName,
			@PageableDefault(sort = "id") Pageable pageable)
			throws ScrapingException {
		List<AudioDataDTO> audioDataDTOs = scraperService
				.getAudioData(query, websiteName, reloadRequired, pageable)
				.stream()
				.map(data -> AudioDataMapper.asAudioDataDTO(data))
				.collect(Collectors.toList());
		if (audioDataDTOs.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(audioDataDTOs);
	}

}
