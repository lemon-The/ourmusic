package com.lemonthe.ourmusic.scraper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * AudioDataRepository
 */
@Repository
public interface AudioDataRepository extends JpaRepository<AudioData, Long> {

	@Query("SELECT a "
			+ "FROM AudioData a "
			+ "WHERE a.searchQuery = ?1")
	List<AudioData> findAllBySearchQuery(String query);

	List<AudioData> findBySearchQueryAndWebsiteName(String query, String websiteName, Pageable pageable);

	boolean existsByWebsiteName(String websiteName);

	void deleteAllByWebsiteName(String websiteName);
}
