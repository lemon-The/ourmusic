package com.lemonthe.ourmusic.audioScraper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AudioDataRepository
 */
@Repository
public interface AudioDataRepository extends JpaRepository<AudioData, Long> {

}
