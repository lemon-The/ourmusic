package com.lemonthe.ourmusic.track;

import java.time.Year;
import java.util.List;

import com.lemonthe.ourmusic.artist.Artist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @ManyToMany
  private List<Artist> artists;

  public Track(String title, Year releaseYear, List<Artist> artists) {
    this.title = title;
    this.releaseYear = releaseYear;
    this.artists = artists;
  }
}
