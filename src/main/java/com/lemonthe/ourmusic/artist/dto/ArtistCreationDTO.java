package com.lemonthe.ourmusic.artist.dto;

import com.lemonthe.ourmusic.artist.Artist;

import jakarta.validation.constraints.NotBlank;

/**
 * ArtistCreationDTO
 */
public record ArtistCreationDTO(
    Long id,
    @NotBlank String name) {

  public Artist asArtist() {
    return new Artist(id, name);
  }
}
