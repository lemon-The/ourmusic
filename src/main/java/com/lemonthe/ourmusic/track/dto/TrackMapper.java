package com.lemonthe.ourmusic.track.dto;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.track.Track;

/**
 * TrackMapper
 */
public class TrackMapper {

	public static Track asTrack(TrackCreationDTO trackDTO) {
		return Track.builder()
			.title(trackDTO.title())
			.releaseYear(trackDTO.releaseYear())
			//.status(ReviewStatus.PENDING)
			.artists(trackDTO.artists())
			.album(trackDTO.album())
			.build();
	}

}
