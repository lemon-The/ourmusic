package com.lemonthe.ourmusic.scraper;

/**
 * AudioScrapingException
 */
public class ScrapingException extends Exception {

	ScrapingException(Throwable cause) {
		super(cause);
	}

	ScrapingException(String message) {
		super(message);
	}
	
}
