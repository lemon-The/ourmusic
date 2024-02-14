package com.lemonthe.ourmusic.track;

import java.time.Year;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Track
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private Year releaseYear; 

  //private final List<Artist> artists;
}
