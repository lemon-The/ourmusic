package com.lemonthe.ourmusic.scraper;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AudioDataRepository
 */
@Repository
public interface AudioDataRepository extends JpaRepository<AudioData, Long> {

	List<AudioData> findBySearchQueryAndWebsiteName(String query, String websiteName, Pageable pageable);

	boolean existsByWebsiteName(String websiteName);

	void deleteAllByWebsiteName(String websiteName);
}
