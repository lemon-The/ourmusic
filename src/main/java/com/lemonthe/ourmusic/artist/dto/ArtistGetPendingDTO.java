package com.lemonthe.ourmusic.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ArtistGetPengingDTO
 */
public record ArtistGetPendingDTO(
		@NotNull Long id,
		@NotNull Long resourceId,
		@NotBlank String name) {
}
