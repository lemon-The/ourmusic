package com.lemonthe.ourmusic.track;

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

import com.lemonthe.ourmusic.track.dto.TrackCreationDTO;

import jakarta.validation.Valid;

/**
 * TrackController
 */
@RestController
@RequestMapping("/track")
public class TrackController {

  @Autowired
  private TrackService trackService;

  @GetMapping
  public ResponseEntity<List<Track>> getTracks() {
    List<Track> tracks = trackService.getTracks();
    if (tracks.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(trackService.getTracks());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTrack(@PathVariable Long id) {
    // TODO find more clean way to handle optional
    return ResponseEntity.ok(trackService
        .getTrack(id)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<?> createTrack(
      @Valid @RequestBody TrackCreationDTO trackDTO,
      Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }
    Track track = trackService.save(trackDTO.asTrack());
    // TODO read more about ServletUriComponentsBuilder
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(track.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  // TODO Should the delete method throw an error if object doesn't exist?
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> deleteTrack(@PathVariable Long id) {
    trackService.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
