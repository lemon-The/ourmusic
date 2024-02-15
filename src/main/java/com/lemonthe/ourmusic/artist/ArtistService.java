package com.lemonthe.ourmusic.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

/**
 * ArtistService
 */
@Service
public class ArtistService {

  @Autowired
  private ArtistRepository artistRepo;

  @Transactional
  public List<Artist> getArtists() {
    return artistRepo.findAll();
  }

  @Transactional
  public Optional<Artist> getArtist(Long id) {
    return artistRepo.findById(id);
  }

  @Transactional
  public Artist save(Artist artist) {
    return artistRepo.save(artist);
  }

  @Transactional
  public void delete(Long id) {
    artistRepo.deleteById(id);
  }
  
}
