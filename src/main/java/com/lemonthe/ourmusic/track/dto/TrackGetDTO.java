package com.lemonthe.ourmusic.track.dto;

import java.time.Year;
import java.util.List;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.artist.Artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * TrackGetDTO
 */
public record TrackGetDTO(
		Long resourceId,
		String title,
		Year releaseYear,
		List<Artist> artists,
		Album album) {
}
