package com.lemonthe.ourmusic.audioScraper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lemonthe.ourmusic.audioScraper.dto.AudioDataDTO;
import com.lemonthe.ourmusic.audioScraper.dto.AudioDataMapper;

/**
 * AudioScraperController
 */
@RestController
@RequestMapping("/scraper")
public class AudioScraperController {

	@Autowired
	private AudioScraperService audioScraperService;

	//@GetMapping
	//public ResponseEntity<List<AudioDataDTO>> getAudioData(
	//		@RequestParam("q") String query) throws AudioScrapingException {
	//	//try {
	//		List<AudioDataDTO> audioDataDTOs = audioScraperService.getAudioData(query)
	//			.stream()
	//			.map(data -> AudioDataMapper.asAudioDataDTO(data))
	//			.collect(Collectors.toList());
	//		if (audioDataDTOs.isEmpty()) {
	//			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//		}
	//		return ResponseEntity.ok(audioDataDTOs);
	//	//} catch (AudioScrapingException e) {
	//		//throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
	//	//}
	//}

	@GetMapping
	public ResponseEntity<List<AudioDataDTO>> getAudioData(
			@RequestParam("q") String query,
			@RequestParam(required = true, value = "src") String audioSourceAlias)
			 throws AudioScrapingException {

		List<AudioDataDTO> audioDataDTOs = audioScraperService.getAudioData(query, AudioSource.asAudioSource(audioSourceAlias))
			.stream()
			.map(data -> AudioDataMapper.asAudioDataDTO(data))
			.collect(Collectors.toList());
		if (audioDataDTOs.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(audioDataDTOs);
	}

}
