package com.lemonthe.ourmusic.album.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AlbumGetDTO
 */
public record AlbumGetDTO(
		@NotNull Long resourceId,
		@NotBlank String title) {
}
