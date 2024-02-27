package com.lemonthe.ourmusic.album.dto;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.review.ReviewStatus;

/**
 * AlbumMapping
 */
public class AlbumMapping {

	public static Album asAlbum(AlbumCreationDTO albumDTO) {
		return Album.builder()
			.id(albumDTO.id())
			.title(albumDTO.title())
			.status(ReviewStatus.PENDING)
			.build();
	}
}
