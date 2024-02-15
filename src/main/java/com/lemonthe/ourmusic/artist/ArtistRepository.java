package com.lemonthe.ourmusic.artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ArtistRepository
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
