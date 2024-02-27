package com.lemonthe.ourmusic.artist.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * ArtistCreationDTO
 */
public record ArtistCreationDTO(
    //Long id,
    @NotBlank String name) {

}
