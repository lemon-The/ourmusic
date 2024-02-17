package com.lemonthe.ourmusic;

import java.time.Year;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.lemonthe.ourmusic.album.Album;
import com.lemonthe.ourmusic.album.AlbumRepository;
import com.lemonthe.ourmusic.artist.Artist;
import com.lemonthe.ourmusic.artist.ArtistRepository;
import com.lemonthe.ourmusic.track.Track;
import com.lemonthe.ourmusic.track.TrackRepository;

@SpringBootApplication
public class OurmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurmusicApplication.class, args);
	}

  // Cosider moving this code in other class
  @Bean
  public CommandLineRunner dataLoader(
      TrackRepository trackRepo,
      ArtistRepository artistRepo,
      AlbumRepository albumRepo) {
    return args -> {
      Artist kish = new Artist("Korol_i_Shut");
      artistRepo.save(kish);

      Track kpg = new Track("Kamnem_po_golove", Year.of(1990), List.of(kish));
      Track dim = new Track("Durak_i_molnia", Year.of(1990), List.of(kish));
      trackRepo.save(kpg);
      trackRepo.save(dim);

      Album kpgAlbum = new Album("Kamnem_po_golove", List.of(kpg, dim));
      albumRepo.save(kpgAlbum);
    };
  }

}
