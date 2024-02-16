package com.lemonthe.ourmusic.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AlbumController
 */
@RestController
@RequestMapping("/album")
public class AlbumController {

  @Autowired
  private AlbumService albumService;

  
}
