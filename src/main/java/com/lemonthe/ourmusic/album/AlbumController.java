package com.lemonthe.ourmusic.album;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lemonthe.ourmusic.album.dto.AlbumCreationDTO;
import com.lemonthe.ourmusic.album.dto.AlbumGetDTO;
import com.lemonthe.ourmusic.album.dto.AlbumGetPendingDTO;
import com.lemonthe.ourmusic.album.dto.AlbumMapper;
import com.lemonthe.ourmusic.user.User;
import com.lemonthe.ourmusic.user.UserService;

import jakarta.validation.Valid;

/**
 * AlbumController
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

  @Autowired
  private AlbumService albumService;

	@Autowired
	private UserService userService;

  @GetMapping
  public ResponseEntity<List<AlbumGetDTO>> getApprovedAlbums() {
		//TODO consider remove error on empty list. It should return empty list (?)
		//TODO Should method return error on empty list???
    List<AlbumGetDTO> albums = albumService.getAlbums()
			.stream()
			.map(a -> AlbumMapper.asAlbumGetDTO(a))
			.collect(Collectors.toList());
    //if (albums.isEmpty()) {
    //  throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    //}
    return ResponseEntity.ok(albums);
  }

	//TODO consider move roles to own class
  @GetMapping("/pending")
  public ResponseEntity<List<AlbumGetPendingDTO>> getUserPendingAlbums(
			@AuthenticationPrincipal UserDetails userDetails) {
		boolean userModerator = userDetails.getAuthorities()
			.stream()
			.map(a -> a.getAuthority())
			.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
		if (userModerator) {
			return ResponseEntity.ok(albumService.getPendingAlbums()
					.stream()
					.map(a -> AlbumMapper.asAlbumGetPengingDTO(a))
					.collect(Collectors.toList()));
		} else {
			User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
			return ResponseEntity.ok(albumService.getPendingAlbums(user)
					.stream()
					.map(a -> AlbumMapper.asAlbumGetPengingDTO(a))
					.collect(Collectors.toList()));
		}
  }

  @GetMapping("/{id}")
  public ResponseEntity<AlbumGetDTO> getApprovedAlbum(@PathVariable Long id) {
    return ResponseEntity.ok(albumService
        .getAlbum(id)
				.map(a -> AlbumMapper.asAlbumGetDTO(a))
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  @GetMapping("/log/{id}")
  public ResponseEntity<List<AlbumGetDTO>> getAlbumHistory(@PathVariable Long id) {
    return ResponseEntity.ok(albumService
        .getAlbumHistory(id)
				.stream()
				.map(a -> AlbumMapper.asAlbumGetDTO(a))
				.collect(Collectors.toList()));
  }

  //@GetMapping("/pending/{id}")
  //public ResponseEntity<AlbumGetDTO> getPendingAlbum(
	//		@PathVariable Long id,
	//		@AuthenticationPrincipal UserDetails userDetails) {
	//	boolean userModerator = userDetails.getAuthorities()
	//		.stream()
	//		.map(a -> a.getAuthority())
	//		.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
	//	if (userModerator) {
	//		return ResponseEntity.ok(albumService
	//				.getPendingAlbum(id)
	//				.map(a -> AlbumMapper.asAlbumGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	} else {
	//		return ResponseEntity.ok(albumService
	//				.getPendingAlbum(id, userDetails.getUsername())
	//				.map(a -> AlbumMapper.asAlbumGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	}
  //}

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	//@PostMapping
  public ResponseEntity<?> createAlbum(
      @Valid @RequestBody AlbumCreationDTO albumDTO,
			@AuthenticationPrincipal UserDetails userDetails,
      Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }

		//TODO replace throw
		User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
		boolean userModerator = userDetails.getAuthorities()
			.stream()
			.map(a -> a.getAuthority())
			.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
		Album album;
		if (userModerator) {
			album = albumService.saveAlbum(AlbumMapper.asAlbum(albumDTO), user);
		} else {
			album = albumService.addAlbumToPremod(AlbumMapper.asAlbum(albumDTO), user);
		}

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(album.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

	//TODO unit these methods
	@PatchMapping("/pending/approve/{id}")
	public ResponseEntity<AlbumGetDTO> approveAlbum(
			@PathVariable Long id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
		return ResponseEntity.ok(AlbumMapper.asAlbumGetDTO(albumService.approvePendingAlbum(id, user)));
	}

	@PatchMapping("/pending/reject/{id}")
	public ResponseEntity<Album> rejectAlbum(@PathVariable Long id) {
		albumService.rejectPendingAlbum(id);
    return ResponseEntity.noContent().build();
	}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
    albumService.deleteAlbum(id);
    return ResponseEntity.noContent().build();
  }
  
}
