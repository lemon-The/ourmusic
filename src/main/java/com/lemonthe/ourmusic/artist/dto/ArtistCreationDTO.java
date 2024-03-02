package com.lemonthe.ourmusic.artist.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * ArtistCreationDTO
 */
public record ArtistCreationDTO(
		Long resourceId,
    @NotBlank String name) {
}
