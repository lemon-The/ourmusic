package com.lemonthe.ourmusic.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TrackRepository
 */
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

}
