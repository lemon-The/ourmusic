package com.lemonthe.ourmusic.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

/**
 * ArtistRepository
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

	List<Artist> findByStatus(ReviewStatus status);

	List<Artist> findByStatusAndSuggestedBy(ReviewStatus status, User suggestedBy);

	@Query("SELECT a " +
			"FROM Artist a " +
			"WHERE a.status = ReviewStatus.ACTUAL AND a.resourceId.id = ?1")
	Optional<Artist> findActualByResourceId(long resourceId);

	@Query("SELECT a " +
			"FROM Artist a " +
			"WHERE a.resourceId.id = ?1 " +
			//"and a.status in (ReviewStatus.ACTUAL, ReviewStatus.OUTDATED)")
			"AND a.status != ReviewStatus.PENDING " +
			"ORDER BY a.creationTime DESC")
	List<Artist> findHistoryByReourceId(Long resourceId);

	@Modifying
	@Query("DELETE FROM Artist a WHERE a.resourceId.id = ?1")
	void deleteAllByResourceId(long resourceId);

}
