package com.lemonthe.ourmusic.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query("SELECT CASE WHEN COUNT(c)> 0 THEN TRUE ELSE FALSE END " +
			"FROM Artist a " +
			"WHERE a.resourceId.id = :#{#oa.resourceId.id} " +
			"AND a.name LIKE :#{#oa.name} ")
	//boolean equalsByNameAndResourceId(@Param("oa") Artist otherArtist);
	boolean equalsByNameAndResourceId(@Param("oa") Artist otherArtist);

}
