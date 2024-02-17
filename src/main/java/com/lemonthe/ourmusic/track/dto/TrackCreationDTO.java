package com.lemonthe.ourmusic.track.dto;

import java.time.Year;
import java.util.List;

import com.lemonthe.ourmusic.artist.Artist;
import com.lemonthe.ourmusic.track.Track;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * TrackCreationDTO
 */
public record TrackCreationDTO(
    Long id,
    @NotBlank String title,
    @NotNull Year releaseYear,
    @NotNull List<Artist> artists) {

  public Track asTrack() {
    return new Track(id, title, releaseYear, artists);
  }
}
