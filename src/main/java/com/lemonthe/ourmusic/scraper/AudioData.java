package com.lemonthe.ourmusic.scraper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AudioScraped
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String artist;
	private String coverURL;  // source server cover location
	private String trackURL;  // source server track location
	private String searchQuery;
	private String resource;

	public AudioData(String title, String artist, String coverURL,
			String trackURL, String searchQuery, String resource) {
		this.title = title;
		this.artist = artist;
		this.coverURL = coverURL;
		this.trackURL = trackURL;
		this.searchQuery = searchQuery;
		this.resource = resource;
	}
}
