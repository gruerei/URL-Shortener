package com.openwt.urlshortener.mapper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.openwt.urlshortener.dto.URLOutDTO;
import com.openwt.urlshortener.model.URLModel;


@Component
public class URLMapper {
	
	String pattern = "dd/MM/yyyy HH:mm:ss";
	DateFormat df = new SimpleDateFormat(pattern);
	
	public URLOutDTO entityToDto(URLModel entity) {
		URLOutDTO urlDTO = new URLOutDTO();
		
		if(null != entity.getLongUrl()) {
			urlDTO.setLongURL(entity.getLongUrl());
		}
		if(null != entity.getShortUrl()) {
			urlDTO.setShortURL(entity.getShortUrl());
		}
		urlDTO.setEnabled(entity.isEnabled());
		
		if(null != entity.getDateCreation()) {
			urlDTO.setDateCreationFormatted(df.format(entity.getDateCreation()));
		}
		
		return urlDTO;
	}
}