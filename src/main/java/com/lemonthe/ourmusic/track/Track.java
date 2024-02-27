package com.lemonthe.ourmusic.track;

import java.time.Year;
import java.util.List;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.artist.Artist;
import com.lemonthe.ourmusic.review.ReviewStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Track
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private Year releaseYear; 
	private ReviewStatus status;

  @ManyToMany
  private List<Artist> artists;

	@ManyToOne
	private Album album;

}
