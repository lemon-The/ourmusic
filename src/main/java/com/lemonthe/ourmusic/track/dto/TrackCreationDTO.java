package com.lemonthe.ourmusic.track.dto;

import java.time.Year;

import com.lemonthe.ourmusic.track.Track;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * TrackCreationDTO
 */
public record TrackCreationDTO(
    Long id,
    @NotBlank String title,
    @NotNull Year releaseYear) {

  public Track asTrack() {
    return new Track(id, title, releaseYear);
  }
}
