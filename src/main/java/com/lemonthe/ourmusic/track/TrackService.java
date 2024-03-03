package com.lemonthe.ourmusic.track;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemonthe.ourmusic.album.AlbumService;
import com.lemonthe.ourmusic.track.TrackService;
import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

/**
 * TrackService
 */
@Service
public class TrackService {

  @Autowired
  private TrackRepository trackRepo;

	@Autowired
	private TrackResourceIdRepository trackResourceIdRepo;

  @Autowired
	private TrackService trackService;

  @Autowired
	private AlbumService albumService;

  @Transactional
  public List<Track> getTracks() {
    return trackRepo.findByStatus(ReviewStatus.ACTUAL);
  }

  @Transactional
  public List<Track> getPendingTracks() {
    return trackRepo.findByStatus(ReviewStatus.PENDING);
  }

  @Transactional
  public List<Track> getPendingTracks(User user) {
    return trackRepo.findByStatusAndSuggestedBy(ReviewStatus.PENDING, user);
  }

  @Transactional
  public Optional<Track> getTrack(long resourceId) {
    return trackRepo.findActualByResourceId(resourceId);
  }

	public List<Track> getTrackHistory(long resourceId) {
		return trackRepo.findHistoryByReourceId(resourceId);
	}

	public Track saveTrack(Track track, User user) {
		if (track.getResourceId() == null || track.getResourceId().getId() == null) {
			track.setResourceId(trackResourceIdRepo.save(new TrackResourceId()));
		} else {
			Track old = trackRepo.findActualByResourceId(track.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		track.setStatus(ReviewStatus.ACTUAL);
		track.setSuggestedBy(user);
		track.setModeratedBy(user);
		track.setCreationTime(LocalDateTime.now());
		track.setApprovingTime(LocalDateTime.now());
    return trackRepo.save(track);
	}

	public Track addTrackToPremod(Track track, User user) {
		//if (track.getResourceId() == null || track.getResourceId().getId() == null) {
		//	track.setResourceId(trackIdRepo.save(new TrackId()));
		//}
		track.setStatus(ReviewStatus.PENDING);
		track.setSuggestedBy(user);
		track.setCreationTime(LocalDateTime.now());
		return trackRepo.save(track);
	}

	//TODO condider unite these methods
	public Track approvePendingTrack(long id, User user) {
		//TODO condider throw cheched exception
		Track track = trackRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		if (track.getResourceId() == null || track.getResourceId().getId() == null) {
			track.setResourceId(trackResourceIdRepo.save(new TrackResourceId()));
		} else {
			Track old = trackRepo.findActualByResourceId(track.getResourceId().getId()).orElseThrow();
			old.setStatus(ReviewStatus.OUTDATED);
		}
		track.setStatus(ReviewStatus.ACTUAL);
		track.setModeratedBy(user);
		track.setApprovingTime(LocalDateTime.now());
		return trackRepo.save(track);
	}

	public void rejectPendingTrack(long id) {
		//TODO condider throw cheched exception
		Track track = trackRepo.findById(id)
				.filter(a -> a.getStatus().equals(ReviewStatus.PENDING))
				.orElseThrow();
		trackRepo.deleteById(id);
	}

	public void deleteTrack(long resourceId) {
    //trackRepo.deleteAllByResourceId(resourceId);
		trackResourceIdRepo.deleteById(resourceId);
	}
  
  
}
