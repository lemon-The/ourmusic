package com.lemonthe.ourmusic.album;

import java.util.List;

import com.lemonthe.ourmusic.track.Track;

import jakarta.persistence.Id;
import lombok.Data;

/**
 * Album
 */
@Data
public class Album {

  @Id
  private Long id;
  private final String title;
  private final List<Track> tracks;
}
