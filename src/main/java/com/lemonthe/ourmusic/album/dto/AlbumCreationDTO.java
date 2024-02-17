package com.lemonthe.ourmusic.album.dto;

import java.util.List;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.track.Track;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AlbumCreationDTO
 */
public record AlbumCreationDTO(
    Long id,
    @NotBlank String title,
    @NotNull List<Track> tracks) {

  public Album asAlbum() {
    return new Album(id, title, tracks);
  }
}
