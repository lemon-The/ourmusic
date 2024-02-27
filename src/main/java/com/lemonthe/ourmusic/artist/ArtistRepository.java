package com.lemonthe.ourmusic.artist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lemonthe.ourmusic.review.ReviewStatus;

/**
 * ArtistRepository
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

	List<Artist> findAllByStatus(ReviewStatus status);

}
