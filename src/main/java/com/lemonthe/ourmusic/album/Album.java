package com.lemonthe.ourmusic.album;

import java.util.List;

import com.lemonthe.ourmusic.track.Track;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Album
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;

  @OneToMany
  private List<Track> tracks;

  public Album(String title, List<Track> tracks) {
    this.title = title;
    this.tracks = tracks;
  }
}
