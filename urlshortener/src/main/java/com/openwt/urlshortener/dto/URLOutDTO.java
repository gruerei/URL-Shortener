package com.openwt.urlshortener.dto;

public class URLOutDTO {
	private String shortURL;
	private String longURL;
	private String dateCreationFormatted;
	boolean enabled;
	
	public URLOutDTO() {}
	
	public URLOutDTO(String shortURL, String longURL) {
		this.shortURL = shortURL;
		this.longURL = longURL;
	}
	
	public String getShortURL() {
		return shortURL;
	}
	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	public String getLongURL() {
		return longURL;
	}
	public void setLongURL(String longURL) {
		this.longURL = longURL;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDateCreationFormatted() {
		return dateCreationFormatted;
	}

	public void setDateCreationFormatted(String dateCreationFormatted) {
		this.dateCreationFormatted = dateCreationFormatted;
	}
	
	
	
}
