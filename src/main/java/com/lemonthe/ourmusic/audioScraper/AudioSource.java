package com.lemonthe.ourmusic.audioScraper;

/**
 * AudioSource
 */
public enum AudioSource {
	HITMO("hm");

	private String alias;

	private AudioSource(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
