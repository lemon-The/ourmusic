package com.lemonthe.ourmusic.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
