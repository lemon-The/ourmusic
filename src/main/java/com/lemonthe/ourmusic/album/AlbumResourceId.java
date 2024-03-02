package com.lemonthe.ourmusic.album;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AlbumResourceId
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResourceId {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(mappedBy = "resourceId", cascade = CascadeType.ALL)
	private List<Album> albums;

	public AlbumResourceId(Long id) {
		this.id = id;
	}
	
}
