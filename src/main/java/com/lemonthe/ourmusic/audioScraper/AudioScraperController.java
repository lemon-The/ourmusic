package com.lemonthe.ourmusic.audioScraper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lemonthe.ourmusic.audioScraper.dto.AudioDataDTO;
import com.lemonthe.ourmusic.audioScraper.dto.AudioDataMapper;

/**
 * AudioScraperController
 */
@RestController
@RequestMapping("scraper/")
public class AudioScraperController {

	@Autowired
	private AudioScraperService audioScraperService;

	@GetMapping
	public ResponseEntity<List<AudioDataDTO>> getAudioData() {
		List<AudioDataDTO> audioDataDTOs = audioScraperService.getAudioData()
			.stream()
			.map(data -> AudioDataMapper.asAudioDataDTO(data))
			.collect(Collectors.toList());
		if (audioDataDTOs.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(audioDataDTOs);
	}
	
}
