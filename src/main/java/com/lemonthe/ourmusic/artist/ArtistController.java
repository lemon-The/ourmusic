package com.lemonthe.ourmusic.artist;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lemonthe.ourmusic.artist.dto.ArtistCreationDTO;
import com.lemonthe.ourmusic.artist.dto.ArtistGetDTO;
import com.lemonthe.ourmusic.artist.dto.ArtistGetPengingDTO;
import com.lemonthe.ourmusic.artist.dto.ArtistMapper;
import com.lemonthe.ourmusic.user.User;
import com.lemonthe.ourmusic.user.UserService;

import jakarta.validation.Valid;

/**
 * ArtistController
 */

@RestController
@RequestMapping("/artist")
public class ArtistController {

  @Autowired
  private ArtistService artistService;

	@Autowired
	private UserService userService;

  @GetMapping
  public ResponseEntity<List<ArtistGetDTO>> getApprovedArtists() {
		//TODO consider remove error on empty list. It should return empty list (?)
		//TODO Should method return error on empty list???
    List<ArtistGetDTO> artists = artistService.getArtists()
			.stream()
			.map(a -> ArtistMapper.asArtistGetDTO(a))
			.collect(Collectors.toList());
    //if (artists.isEmpty()) {
    //  throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    //}
    return ResponseEntity.ok(artists);
  }

	//TODO consider move roles to own class
  @GetMapping("/pending")
  public ResponseEntity<List<ArtistGetPengingDTO>> getUserPendingArtists(
			@AuthenticationPrincipal UserDetails userDetails) {
		boolean userModerator = userDetails.getAuthorities()
			.stream()
			.map(a -> a.getAuthority())
			.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
		if (userModerator) {
			return ResponseEntity.ok(artistService.getPendingArtists()
					.stream()
					.map(a -> ArtistMapper.asArtistGetPengingDTO(a))
					.collect(Collectors.toList()));
		} else {
			User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
			return ResponseEntity.ok(artistService.getPendingArtists(user)
					.stream()
					.map(a -> ArtistMapper.asArtistGetPengingDTO(a))
					.collect(Collectors.toList()));
		}
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArtistGetDTO> getApprovedArtist(@PathVariable Long id) {
    return ResponseEntity.ok(artistService
        .getArtist(id)
				.map(a -> ArtistMapper.asArtistGetDTO(a))
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  @GetMapping("/log/{id}")
  public ResponseEntity<List<ArtistGetDTO>> getArtistHistory(@PathVariable Long id) {
    return ResponseEntity.ok(artistService
        .getArtistHistory(id)
				.stream()
				.map(a -> ArtistMapper.asArtistGetDTO(a))
				.collect(Collectors.toList()));
  }

  //@GetMapping("/pending/{id}")
  //public ResponseEntity<ArtistGetDTO> getPendingArtist(
	//		@PathVariable Long id,
	//		@AuthenticationPrincipal UserDetails userDetails) {
	//	boolean userModerator = userDetails.getAuthorities()
	//		.stream()
	//		.map(a -> a.getAuthority())
	//		.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
	//	if (userModerator) {
	//		return ResponseEntity.ok(artistService
	//				.getPendingArtist(id)
	//				.map(a -> ArtistMapper.asArtistGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	} else {
	//		return ResponseEntity.ok(artistService
	//				.getPendingArtist(id, userDetails.getUsername())
	//				.map(a -> ArtistMapper.asArtistGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	}
  //}

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	//@PostMapping
  public ResponseEntity<?> createArtist(
      @Valid @RequestBody ArtistCreationDTO artistDTO,
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
		Artist artist;
		if (userModerator) {
			artist = artistService.saveArtist(ArtistMapper.asArtist(artistDTO), user);
		} else {
			artist = artistService.addArtistToPremod(ArtistMapper.asArtist(artistDTO), user);
		}

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(artist.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

	//TODO unit these methods
	@PatchMapping("/pending/approve/{id}")
	public ResponseEntity<ArtistGetDTO> approveArtist(
			@PathVariable Long id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
		return ResponseEntity.ok(ArtistMapper.asArtistGetDTO(artistService.approvePendingArtist(id, user)));
	}

	@PatchMapping("/pending/reject/{id}")
	public ResponseEntity<Artist> rejectArtist(@PathVariable Long id) {
		artistService.rejectPendingArtist(id);
    return ResponseEntity.noContent().build();
	}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
    artistService.deleteArtist(id);
    return ResponseEntity.noContent().build();
  }
  
}
