package com.lemonthe.ourmusic.album;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

/**
 * AlbumService
 */
@Service
@Transactional
public class AlbumService {

  @Autowired
  private AlbumRepository albumRepo;

	@Autowired
	private AlbumResourceIdRepository albumResourceIdRepo;

  public List<Album> getAlbums() {
    return albumRepo.findByStatus(ReviewStatus.ACTUAL);
  }

  public List<Album> getPendingAlbums() {
    return albumRepo.findByStatus(ReviewStatus.PENDING);
  }

  public List<Album> getPendingAlbums(User user) {
    return albumRepo.findByStatusAndSuggestedBy(ReviewStatus.PENDING, user);
  }

  public Optional<Album> getAlbum(long resourceId) {
    return albumRepo.findActualByResourceId(resourceId);
  }

	public List<Album> getAlbumHistory(long resourceId) {
		return albumRepo.findHistoryByReourceId(resourceId);
	}

  public Album saveAlbum(Album album, User user) {
		if (album.getResourceId() == null || album.getResourceId().getId() == null) {
			album.setResourceId(albumResourceIdRepo.save(new AlbumResourceId()));
		} else {
			Album old = albumRepo.findActualByResourceId(album.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		album.setStatus(ReviewStatus.ACTUAL);
		album.setSuggestedBy(user);
		album.setModeratedBy(user);
		album.setCreationTime(LocalDateTime.now());
		album.setApprovingTime(LocalDateTime.now());
    return albumRepo.save(album);
  }

	public Album addAlbumToPremod(Album album, User user) {
		//if (album.getResourceId() == null || album.getResourceId().getId() == null) {
		//	album.setResourceId(albumIdRepo.save(new AlbumId()));
		//}
		album.setStatus(ReviewStatus.PENDING);
		album.setSuggestedBy(user);
		album.setCreationTime(LocalDateTime.now());
		return albumRepo.save(album);
	}

	public Album approvePendingAlbum(long id, User user) {
		//TODO condider throw cheched exception
		Album album = albumRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		if (album.getResourceId() == null || album.getResourceId().getId() == null) {
			album.setResourceId(albumResourceIdRepo.save(new AlbumResourceId()));
		} else {
			Album old = albumRepo.findActualByResourceId(album.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		album.setStatus(ReviewStatus.ACTUAL);
		album.setModeratedBy(user);
		album.setApprovingTime(LocalDateTime.now());
		return albumRepo.save(album);
	}

	public void rejectPendingAlbum(long id) {
		//TODO condider throw cheched exception
		Album album = albumRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		albumRepo.deleteById(id);
	}

	public void deleteAlbum(long resourceId) {
    //albumRepo.deleteAllByResourceId(resourceId);
		albumResourceIdRepo.deleteById(resourceId);
	}

}
