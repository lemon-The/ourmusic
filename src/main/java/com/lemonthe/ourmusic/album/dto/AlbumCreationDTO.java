package com.lemonthe.ourmusic.album.dto;

import com.lemonthe.ourmusic.album.Album;

import jakarta.validation.constraints.NotBlank;

/**
 * AlbumCreationDTO
 */
public record AlbumCreationDTO(
    Long id,
    @NotBlank String title) {
}
