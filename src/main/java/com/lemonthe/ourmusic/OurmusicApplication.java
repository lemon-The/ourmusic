package com.lemonthe.ourmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OurmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(OurmusicApplication.class, args);
	}

  // Cosider moving this code in other class
  //@Bean
  //public CommandLineRunner dataLoader(
  //    TrackRepository trackRepo,
  //    ArtistRepository artistRepo,
  //    AlbumRepository albumRepo) {
  //  return args -> {
  //    Artist kish = new Artist("Korol_i_Shut");
  //    artistRepo.save(kish);

  //    Track kpg = new Track("Kamnem_po_golove", Year.of(1990), List.of(kish));
  //    Track dim = new Track("Durak_i_molnia", Year.of(1990), List.of(kish));
  //    trackRepo.save(kpg);
  //    trackRepo.save(dim);

  //    Album kpgAlbum = new Album("Kamnem_po_golove", List.of(kpg, dim));
  //    albumRepo.save(kpgAlbum);
  //  };
  //}

}
