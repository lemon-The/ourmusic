package com.lemonthe.ourmusic.audioScraper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AudioScraperService
 */
@Service
public class AudioScraperService {

	@Autowired
	private AudioDataRepository audioDataRepo;

	@Transactional
	public List<AudioData> getAudioData() {
		return audioDataRepo.findAll();
	}
	
}
