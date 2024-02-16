package com.lemonthe.ourmusic.album;

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

import com.lemonthe.ourmusic.album.dto.AlbumCreationDTO;

import jakarta.validation.Valid;

/**
 * AlbumController
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

  @Autowired
  private AlbumService albumService;

  @GetMapping
  public ResponseEntity<List<Album>> getAlbums() {
    List<Album> albums = albumService.getAlbums();
    if (albums.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(albums);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Album> getAlbum(@PathVariable Long id) {
    return ResponseEntity.ok(albumService
        .getAlbum(id)
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<?> createAlbum(
      @Valid @RequestBody AlbumCreationDTO albumDTO,
      Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }
    Album album = albumService.save(albumDTO.asAlbum());
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(album.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
    albumService.delete(id);
    return ResponseEntity.noContent().build();
  }
  
}
