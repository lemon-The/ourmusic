package com.lemonthe.ourmusic.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * ArtistIdRepository
 */
public interface ArtistIdRepository extends JpaRepository<ArtistId, Long> {

	//@Modifying
	//@Query("DELETE FROM Artist a WHERE a.resourceId.id = ?1;" +
	//"DELETE FROM ArtistId ai WHERE ai.resourceId.id = ?1;")
	//void deleteAllByResourceId(long resourceId);
	
}
