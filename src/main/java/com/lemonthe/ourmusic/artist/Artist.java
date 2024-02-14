package com.lemonthe.ourmusic.artist;

import jakarta.persistence.Id;
import lombok.Data;

/**
 * Artist
 */
@Data
public class Artist {

  @Id
  private Long id;
  private final String name;
}
