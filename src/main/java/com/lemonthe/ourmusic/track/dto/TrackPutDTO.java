package com.lemonthe.ourmusic.track.dto;

import java.time.Year;
import java.util.List;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.artist.Artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * TrackPutDTO
 */
public record TrackPutDTO(
    Long resourceId,
    @NotBlank String title,
    @NotNull Year releaseYear,
    @NotNull List<Artist> artists,
		@NotNull Album album) {
}
