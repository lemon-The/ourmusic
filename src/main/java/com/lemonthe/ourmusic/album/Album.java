package com.lemonthe.ourmusic.album;

import java.time.LocalDateTime;

import com.lemonthe.ourmusic.review.ReviewStatus;
import com.lemonthe.ourmusic.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Album
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

	@ManyToOne
	private AlbumResourceId resourceId;
  private String title;
	private ReviewStatus status;

	private LocalDateTime creationTime;
	private LocalDateTime approvingTime;

	@ManyToOne
	private User suggestedBy;

	@ManyToOne
	private User moderatedBy;

}
