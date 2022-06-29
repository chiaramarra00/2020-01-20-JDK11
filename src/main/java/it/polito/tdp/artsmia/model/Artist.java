package it.polito.tdp.artsmia.model;

import java.util.Objects;

public class Artist {
	
	private int artistId;
	private String name;
	
	public Artist(int artistId, String name) {
		super();
		this.artistId = artistId;
		this.name = name;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(artistId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		return artistId == other.artistId;
	}

}
