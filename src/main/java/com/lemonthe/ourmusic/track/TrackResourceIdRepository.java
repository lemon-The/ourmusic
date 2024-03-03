package com.lemonthe.ourmusic.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TrackResourceIdRepository
 */
@Repository
public interface TrackResourceIdRepository extends JpaRepository<TrackResourceId, Long> {

	
}
