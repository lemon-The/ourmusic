package com.lemonthe.ourmusic.track;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TrackService
 */
@Service
public class TrackService {

  @Autowired
  private TrackRepository trackRepo;

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

  @Transactional
  public void delete(Long id) {
    trackRepo.deleteById(id);
  }
  
}
