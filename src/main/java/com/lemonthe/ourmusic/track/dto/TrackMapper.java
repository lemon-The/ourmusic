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

	//public static Track asTrack(TrackPutDTO trackDTO) {
	//	return Track.builder()
	//		.resourceId(new TrackId(trackDTO.resourceId()))
	//		.name(trackDTO.name())
	//		//.suggestedBy(suggestedBy)
	//		.build();
	//}

	public static TrackGetDTO asTrackGetDTO(Track track) {
		return new TrackGetDTO(track.getResourceId().getId(), track.getTitle());
	}

	public static TrackGetPendingDTO asTrackGetPengingDTO(Track track) {
		if (track.getResourceId() == null)
			return new TrackGetPendingDTO(track.getId(), null, track.getName());
		else
			return new TrackGetPendingDTO(track.getId(), track.getResourceId().getId(), track.getName());
		
	}

}
