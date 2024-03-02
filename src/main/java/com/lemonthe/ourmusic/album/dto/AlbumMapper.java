package com.lemonthe.ourmusic.album.dto;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.album.AlbumResourceId;

/**
 * AlbumMapping
 */
public class AlbumMapper {

	public static Album asAlbum(AlbumCreationDTO albumDTO) {
		return Album.builder()
			.resourceId(new AlbumResourceId(albumDTO.resourceId()))
			.title(albumDTO.title())
			//.suggestedBy(suggestedBy)
			.build();
	}

	public static Album asAlbum(AlbumPutDTO albumDTO) {
		return Album.builder()
			.resourceId(new AlbumResourceId(albumDTO.resourceId()))
			.title(albumDTO.name())
			//.suggestedBy(suggestedBy)
			.build();
	}

	public static AlbumGetDTO asAlbumGetDTO(Album album) {
		return new AlbumGetDTO(album.getResourceId().getId(), album.getTitle());
	}

	public static AlbumGetPendingDTO asAlbumGetPengingDTO(Album album) {
		if (album.getResourceId() == null)
			return new AlbumGetPendingDTO(album.getId(), null, album.getTitle());
		else
			return new AlbumGetPendingDTO(album.getId(), album.getResourceId().getId(), album.getTitle());
		
	}

}
