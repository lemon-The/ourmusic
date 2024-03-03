package com.lemonthe.ourmusic.track;

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

import com.lemonthe.ourmusic.track.dto.TrackCreationDTO;
import com.lemonthe.ourmusic.track.dto.TrackGetDTO;
import com.lemonthe.ourmusic.track.dto.TrackMapper;
import com.lemonthe.ourmusic.user.User;
import com.lemonthe.ourmusic.user.UserService;

import jakarta.validation.Valid;

/**
 * TrackController
 */
@RestController
@RequestMapping("/track")
public class TrackController {

  @Autowired
  private TrackService trackService;

	@Autowired
	private UserService userService;

  @GetMapping
  public ResponseEntity<List<TrackGetDTO>> getApprovedTracks() {
		//TODO consider remove error on empty list. It should return empty list (?)
		//TODO Should method return error on empty list???
    List<TrackGetDTO> tracks = trackService.getTracks()
			.stream()
			.map(t -> TrackMapper.asTrackGetDTO(t))
			.collect(Collectors.toList());
    //if (tracks.isEmpty()) {
    //  throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    //}
    return ResponseEntity.ok(tracks);
  }

	//TODO consider move roles to own class
  @GetMapping("/pending")
  public ResponseEntity<List<TrackGetPendingDTO>> getUserPendingTracks(
			@AuthenticationPrincipal UserDetails userDetails) {
		boolean userModerator = userDetails.getAuthorities()
			.stream()
			.map(a -> a.getAuthority())
			.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
		if (userModerator) {
			return ResponseEntity.ok(trackService.getPendingTracks()
					.stream()
					.map(a -> TrackMapper.asTrackGetPengingDTO(a))
					.collect(Collectors.toList()));
		} else {
			User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
			return ResponseEntity.ok(trackService.getPendingTracks(user)
					.stream()
					.map(a -> TrackMapper.asTrackGetPengingDTO(a))
					.collect(Collectors.toList()));
		}
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrackGetDTO> getApprovedTrack(@PathVariable Long id) {
    return ResponseEntity.ok(trackService
        .getTrack(id)
				.map(a -> TrackMapper.asTrackGetDTO(a))
        .orElseThrow(() -> {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }));
  }

  @GetMapping("/log/{id}")
  public ResponseEntity<List<TrackGetDTO>> getTrackHistory(@PathVariable Long id) {
    return ResponseEntity.ok(trackService
        .getTrackHistory(id)
				.stream()
				.map(a -> TrackMapper.asTrackGetDTO(a))
				.collect(Collectors.toList()));
  }

  //@GetMapping("/pending/{id}")
  //public ResponseEntity<TrackGetDTO> getPendingTrack(
	//		@PathVariable Long id,
	//		@AuthenticationPrincipal UserDetails userDetails) {
	//	boolean userModerator = userDetails.getAuthorities()
	//		.stream()
	//		.map(a -> a.getAuthority())
	//		.anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_MODERATOR"));
	//	if (userModerator) {
	//		return ResponseEntity.ok(trackService
	//				.getPendingTrack(id)
	//				.map(a -> TrackMapper.asTrackGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	} else {
	//		return ResponseEntity.ok(trackService
	//				.getPendingTrack(id, userDetails.getUsername())
	//				.map(a -> TrackMapper.asTrackGetDTO(a))
	//				.orElseThrow(() -> {
	//					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	//				}));
	//	}
  //}

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	//@PostMapping
  public ResponseEntity<?> createTrack(
      @Valid @RequestBody TrackCreationDTO trackDTO,
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
		Track track;
		if (userModerator) {
			track = trackService.saveTrack(TrackMapper.asTrack(trackDTO), user);
		} else {
			track = trackService.addTrackToPremod(TrackMapper.asTrack(trackDTO), user);
		}

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(track.getId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

	//TODO unit these methods
	@PatchMapping("/pending/approve/{id}")
	public ResponseEntity<TrackGetDTO> approveTrack(
			@PathVariable Long id,
			@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
		return ResponseEntity.ok(TrackMapper.asTrackGetDTO(trackService.approvePendingTrack(id, user)));
	}

	@PatchMapping("/pending/reject/{id}")
	public ResponseEntity<Track> rejectTrack(@PathVariable Long id) {
		trackService.rejectPendingTrack(id);
    return ResponseEntity.noContent().build();
	}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteTrack(@PathVariable Long id) {
    trackService.deleteTrack(id);
    return ResponseEntity.noContent().build();
  }
  
}
