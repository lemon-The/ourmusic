package com.lemonthe.ourmusic.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * ArtistGetDTO
 */

public record ArtistGetDTO(
		@NotNull Long resourceId,
		@NotBlank String name) {
}
