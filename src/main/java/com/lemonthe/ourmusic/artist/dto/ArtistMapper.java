package com.lemonthe.ourmusic.artist.dto;

import com.lemonthe.ourmusic.artist.Artist;
import com.lemonthe.ourmusic.review.ReviewStatus;

/**
 * ArtistMapper
 */
public class ArtistMapper {

	public static Artist asArtist(ArtistCreationDTO artistDTO) {
		return Artist.builder()
			//.id(artistDTO.id())
			.name(artistDTO.name())
			.build();
	}
}
