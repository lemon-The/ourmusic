package com.lemonthe.ourmusic.artist;

import com.lemonthe.ourmusic.review.ReviewStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artist
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
	/* 
	 * id of an existing artist object that needs to be corrected (in case this 
	 * is a correction, otherwise it'll be ignored)
	*/
	//private Long originalArtistId;
  private String name;
	private ReviewStatus status;

}
