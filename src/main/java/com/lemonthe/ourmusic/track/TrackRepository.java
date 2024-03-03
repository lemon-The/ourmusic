package com.lemonthe.ourmusic.track;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

/**
 * TrackRepository
 */
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

	List<Track> findByStatus(ReviewStatus status);

	List<Track> findByStatusAndSuggestedBy(ReviewStatus status, User suggestedBy);

	@Query("SELECT a " +
			"FROM Track a " +
			"WHERE a.status = ReviewStatus.ACTUAL AND a.resourceId.id = ?1")
	Optional<Track> findActualByResourceId(long resourceId);

	@Query("SELECT a " +
			"FROM Track a " +
			"WHERE a.resourceId.id = ?1 " +
			//"and a.status in (ReviewStatus.ACTUAL, ReviewStatus.OUTDATED)")
			"AND a.status != ReviewStatus.PENDING " +
			"ORDER BY a.creationTime DESC")
	List<Track> findHistoryByReourceId(Long resourceId);

	@Modifying
	@Query("DELETE FROM Track a WHERE a.resourceId.id = ?1")
	void deleteAllByResourceId(long resourceId);

}
