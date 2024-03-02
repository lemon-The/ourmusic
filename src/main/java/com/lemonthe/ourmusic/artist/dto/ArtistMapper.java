package com.lemonthe.ourmusic.artist.dto;

import com.lemonthe.ourmusic.artist.Artist;
import com.lemonthe.ourmusic.artist.ArtistId;
import com.lemonthe.ourmusic.user.User;

/**
 * ArtistMapper
 */
public class ArtistMapper {

	public static Artist asArtist(ArtistCreationDTO artistDTO) {
		return Artist.builder()
			.resourceId(new ArtistId(artistDTO.resourceId()))
			.name(artistDTO.name())
			//.suggestedBy(suggestedBy)
			.build();
	}

	public static Artist asArtist(ArtistPutDTO artistDTO) {
		return Artist.builder()
			.resourceId(new ArtistId(artistDTO.resourceId()))
			.name(artistDTO.name())
			//.suggestedBy(suggestedBy)
			.build();
	}

	public static ArtistGetDTO asArtistGetDTO(Artist artist) {
		return new ArtistGetDTO(artist.getResourceId().getId(), artist.getName());
	}

	public static ArtistGetPengingDTO asArtistGetPengingDTO(Artist artist) {
		if (artist.getResourceId() == null)
			return new ArtistGetPengingDTO(artist.getId(), null, artist.getName());
		else
			return new ArtistGetPengingDTO(artist.getId(), artist.getResourceId().getId(), artist.getName());
		
	}

}
