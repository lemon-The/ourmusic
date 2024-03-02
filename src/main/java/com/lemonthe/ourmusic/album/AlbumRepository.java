package com.lemonthe.ourmusic.album;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

	List<Album> findByStatus(ReviewStatus status);

	List<Album> findByStatusAndSuggestedBy(ReviewStatus status, User suggestedBy);

	@Query("SELECT a " +
			"FROM Album a " +
			"WHERE a.status = ReviewStatus.ACTUAL AND a.resourceId.id = ?1")
	Optional<Album> findActualByResourceId(long resourceId);

	@Query("SELECT a " +
			"FROM Album a " +
			"WHERE a.resourceId.id = ?1 " +
			"AND a.status != ReviewStatus.PENDING " +
			"ORDER BY a.creationTime DESC")
	List<Album> findHistoryByReourceId(Long resourceId);

	@Modifying
	@Query("DELETE FROM Album a WHERE a.resourceId.id = ?1")
	void deleteAllByResourceId(long resourceId);

}
