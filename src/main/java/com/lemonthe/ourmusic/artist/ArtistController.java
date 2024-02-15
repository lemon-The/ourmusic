package com.lemonthe.ourmusic.artist;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lemonthe.ourmusic.artist.dto.ArtistCreationDTO;

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
    List<Artist> artists = artistService.getArtists();
    if (artists.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<?> createArtist(
      @Valid @RequestBody ArtistCreationDTO artistDTO,
      Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }
    Artist artist = artistService.save(artistDTO.asArtist());
    URI location =ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(artist.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
    artistService.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
