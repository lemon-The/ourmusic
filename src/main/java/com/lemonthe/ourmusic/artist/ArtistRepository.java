package com.lemonthe.ourmusic.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
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

	List<Artist> findByStatusIsNotIn(List<ReviewStatus> statuses);

	//List<Artist> findByStatusIsNotInAndSuggestedBy(List<ReviewStatus> statuses, String suggestedBy);

	@Query("SELECT a " +
			"FROM Artist a " +
			"WHERE a.status = ReviewStatus.ACTUAL AND a.resourceId.id = ?1")
	Optional<Artist> findActualByResourceId(long resourceId);

	//List<Artist> findByResourceIdAndStatus(Long resourceId, ReviewStatus status);

	@Query("SELECT a " +
			"FROM Artist a " +
			"WHERE a.resourceId.id = ?1 " +
			//"and a.status in (ReviewStatus.ACTUAL, ReviewStatus.OUTDATED)")
			"AND a.status != ReviewStatus.PENDING " +
			"ORDER BY a.creationTime DESC")
	List<Artist> findHistoryByReourceId(Long resourceId);

	//void deleteByStatus(ReviewStatus status);

	@Modifying
	@Query("DELETE FROM Artist a WHERE a.resourceId.id = ?1")
	void deleteAllByResourceId(long resourceId);

}
