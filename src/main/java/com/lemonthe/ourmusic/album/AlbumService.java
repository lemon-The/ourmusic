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
@Transactional
public class AlbumService {

  @Autowired
  private AlbumRepository albumRepo;

  public List<Album> getAlbums() {
    return albumRepo.findAll();
  }

  public Optional<Album> getAlbum(Long id) {
    return albumRepo.findById(id);
  }

  public Album save(Album album) {
    return albumRepo.save(album);
  }

  public void delete(Long id) {
    albumRepo.deleteById(id);
  }

	public boolean existsById(Long id) {
		return albumRepo.existsById(id);
	}
  
}
