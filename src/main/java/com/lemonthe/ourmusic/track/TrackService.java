package com.lemonthe.ourmusic.track;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemonthe.ourmusic.album.AlbumRepository;
import com.lemonthe.ourmusic.album.AlbumService;
import com.lemonthe.ourmusic.artist.ArtistRepository;
import com.lemonthe.ourmusic.artist.ArtistService;

/**
 * TrackService
 */
@Service
public class TrackService {

  @Autowired
  private TrackRepository trackRepo;

  @Autowired
	private ArtistService artistService;

  @Autowired
	private AlbumService albumService;

  @Transactional
  public List<Track> getTracks() {
    return trackRepo.findAll();
  }

  @Transactional
  public Optional<Track> getTrack(Long id) {
    return trackRepo.findById(id);
  }

  @Transactional
  public Track save(Track track) {
    return trackRepo.save(track);
  }

	//@Transactional
	//public Track addToPremod(Track track) {
	//	if (!albumService.existsById(track.getAlbum().getId())) {

	//	}
	//}

  @Transactional
  public void delete(Long id) {
    trackRepo.deleteById(id);
  }
  
}
