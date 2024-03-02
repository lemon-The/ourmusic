package com.lemonthe.ourmusic.artist;

import java.time.LocalDateTime;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

	//TODO describe this field
	@ManyToOne
	private ArtistId resourceId;
  private String name;
	private ReviewStatus status;

	//@OneToOne
	//private Artist previousVersion;

	private LocalDateTime creationTime;
	private LocalDateTime approvingTime;

	@ManyToOne
	private User suggestedBy;

	@ManyToOne
	private User moderatedBy;

}
