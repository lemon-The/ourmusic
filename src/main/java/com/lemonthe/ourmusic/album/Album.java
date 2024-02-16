package com.lemonthe.ourmusic.album;

import java.util.List;

import com.lemonthe.ourmusic.track.Track;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
  private Long id;
  private String title;
  //private List<Track> tracks;
}
