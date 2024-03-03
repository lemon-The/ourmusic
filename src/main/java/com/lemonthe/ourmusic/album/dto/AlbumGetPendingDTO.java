package com.lemonthe.ourmusic.album.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AlbymGetPendingDTO
 */
public record AlbumGetPendingDTO(
		@NotNull Long id,
		@NotNull Long resourceId,
		@NotBlank String title) {
}
