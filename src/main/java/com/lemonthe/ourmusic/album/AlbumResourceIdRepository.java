package com.lemonthe.ourmusic.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AlbumResourceIdRepository
 */
@Repository
public interface AlbumResourceIdRepository extends JpaRepository<AlbumResourceId, Long>{
	
}
