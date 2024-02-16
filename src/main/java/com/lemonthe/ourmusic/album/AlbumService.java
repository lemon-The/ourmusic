package com.lemonthe.ourmusic.album;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AlbumService
 */
@Service
public class AlbumService {

  @Autowired
  private AlbumRepository albumRepo;

  @Transactional
  public List<Album> getAlbums() {
    return albumRepo.findAll();
  }

  @Transactional
  public Optional<Album> getAlbum(Long id) {
    return albumRepo.findById(id);
  }

  @Transactional
  public Album save(Album album) {
    return albumRepo.save(album);
  }

  @Transactional
  public void delete(Long id) {
    albumRepo.deleteById(id);
  }
  
}
