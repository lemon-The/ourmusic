package com.lemonthe.ourmusic.artist;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lemonthe.ourmusic.artist.dto.ArtistCreationDTO;
import com.lemonthe.ourmusic.artist.dto.ArtistMapper;

import jakarta.validation.Valid;

/**
 * ArtistController
 */

@RestController
@RequestMapping("/artist")
public class ArtistController {

  @Autowired
  private ArtistService artistService;

  @GetMapping
  public ResponseEntity<List<Artist>> getArtists() {
		//TODO consider remove error on empty list. It should return empty list (?)
    List<Artist> artists = artistService.getArtists();
    if (artists.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(artists);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
    return ResponseEntity.ok(artistService
        .getArtist(id)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  //@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	@PostMapping
  public ResponseEntity<?> createArtist(
      @Valid @RequestBody ArtistCreationDTO artistDTO,
      Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }
    Artist artist = artistService.addToPremod(ArtistMapper.asArtist(artistDTO));
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(artist.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

	@GetMapping("/pending")
	public ResponseEntity<List<Artist>> getPendingArtists() {
		List<Artist> pendigArtists = artistService.getPendigArtists();
		if (pendigArtists.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(pendigArtists);
	}

	@PatchMapping("/approve/{id}")
	public ResponseEntity<Artist> approveArtist(@PathVariable Long id) {
		return ResponseEntity.ok(artistService.approveArtist(id));
	}

	@PatchMapping("/reject/{id}")
	public ResponseEntity<Artist> rejectArtist(@PathVariable Long id) {
		return ResponseEntity.ok(artistService.rejectArtist(id));
	}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
    artistService.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
