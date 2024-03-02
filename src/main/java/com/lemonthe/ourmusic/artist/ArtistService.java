package com.lemonthe.ourmusic.artist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;
import com.lemonthe.ourmusic.user.UserService;

import jakarta.transaction.Transactional;

/**
 * ArtistService
 */
@Service
@Transactional
public class ArtistService {

  @Autowired
  private ArtistRepository artistRepo;

  @Autowired
  private ArtistIdRepository artistIdRepo;

	public List<Artist> getArtists() {
		return artistRepo.findByStatus(ReviewStatus.ACTUAL);
	}

	public List<Artist> getPendingArtists() {
		return artistRepo.findByStatus(ReviewStatus.PENDING);
	}

	public List<Artist> getPendingArtists(User user) {
		return artistRepo.findByStatusAndSuggestedBy(ReviewStatus.PENDING, user);
	}

	public Optional<Artist> getArtist(long resourceId) {
		return artistRepo.findActualByResourceId(resourceId);
	}

	//TODO sort result
	public List<Artist> getArtistHistory(long resourceId) {
		return artistRepo.findHistoryByReourceId(resourceId);
	}

  public Artist saveArtist(Artist artist, User user) {
		if (artist.getResourceId() == null || artist.getResourceId().getId() == null) {
			artist.setResourceId(artistIdRepo.save(new ArtistId()));
		} else {
			Artist old = artistRepo.findActualByResourceId(artist.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		artist.setStatus(ReviewStatus.ACTUAL);
		artist.setSuggestedBy(user);
		artist.setModeratedBy(user);
		artist.setCreationTime(LocalDateTime.now());
		artist.setApprovingTime(LocalDateTime.now());
    return artistRepo.save(artist);
  }

	public Artist addArtistToPremod(Artist artist, User user) {
		//if (artist.getResourceId() == null || artist.getResourceId().getId() == null) {
		//	artist.setResourceId(artistIdRepo.save(new ArtistId()));
		//}
		artist.setStatus(ReviewStatus.PENDING);
		artist.setSuggestedBy(user);
		artist.setCreationTime(LocalDateTime.now());
		return artistRepo.save(artist);
	}

	//TODO condider unite these methods
	public Artist approvePendingArtist(long id, User user) {
		//TODO condider throw cheched exception
		Artist artist = artistRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		if (artist.getResourceId() == null || artist.getResourceId().getId() == null) {
			artist.setResourceId(artistIdRepo.save(new ArtistId()));
		} else {
			Artist old = artistRepo.findActualByResourceId(artist.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		artist.setStatus(ReviewStatus.ACTUAL);
		artist.setModeratedBy(user);
		artist.setApprovingTime(LocalDateTime.now());
		return artistRepo.save(artist);
	}

	public void rejectPendingArtist(long id) {
		//TODO condider throw cheched exception
		Artist artist = artistRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		artistRepo.deleteById(id);
	}

	public void deleteArtist(long resourceId) {
    //artistRepo.deleteAllByResourceId(resourceId);
		artistIdRepo.deleteById(resourceId);
	}
  
}
