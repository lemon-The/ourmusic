package com.lemonthe.ourmusic.artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lemonthe.ourmusic.review.ReviewStatus;

import jakarta.transaction.Transactional;

/**
 * ArtistService
 */
@Service
@Transactional
public class ArtistService {

  @Autowired
  private ArtistRepository artistRepo;

  public List<Artist> getApprovedArtists() {
    return artistRepo.findAllByStatus(ReviewStatus.APPROVED);
  }

	public List<Artist> getPendigArtists() {
		return artistRepo.findAllByStatus(ReviewStatus.PENDING);
	}

  public Optional<Artist> getArtist(Long id) {
    return artistRepo.findById(id);
  }

  public Artist save(Artist artist) {
    return artistRepo.save(artist);
  }

	public Artist addToPremod(Artist artist) {
		artist.setStatus(ReviewStatus.PENDING);
		return artistRepo.save(artist);
	}

	//TODO condider unite these methods
	public Artist approveArtist(Long id) {
		//TODO condider throw cheched exception
		Artist artist = artistRepo.findById(id).orElseThrow();
		artist.setStatus(ReviewStatus.APPROVED);
		return artistRepo.save(artist);
	}

	public Artist rejectArtist(Long id) {
		//TODO condider throw cheched exception
		Artist artist = artistRepo.findById(id).orElseThrow();
		artist.setStatus(ReviewStatus.REJECTED);
		return artistRepo.save(artist);
	}


  public void delete(Long id) {
    artistRepo.deleteById(id);
  }

	public boolean existsById(Long id) {
		return artistRepo.existsById(id);
	}
  
}
