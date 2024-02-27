package com.lemonthe.ourmusic.album;

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
  private String title;
	private ReviewStatus status;

}
