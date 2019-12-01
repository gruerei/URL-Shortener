package com.openwt.urlshortener.service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openwt.urlshortener.model.URLModel;
import com.openwt.urlshortener.repository.URLRepository;


@Service
public class URLService {

	@Autowired
	private URLRepository uRLRepository;
	
	public URLModel createURL(String longURL, String shortUrl) {
		return uRLRepository.save(new URLModel(longURL, shortUrl ,Calendar.getInstance(Locale.FRANCE).getTime()));
	}
	
	public List<URLModel> getAllOrderedByDateCreation() {
		return uRLRepository.findAllByOrderByDateCreationAsc();
	}
	
	public void deleteURL(Long id) {
		uRLRepository.deleteById(id);
	}
	
	public URLModel getById(long id) {
		return uRLRepository.findById(id).orElse(null);
	}
	
	public URLModel findOneByLongURL(String longURL) {
		return uRLRepository.findTopByLongUrl(longURL);
	}
	
	public URLModel changeEnablemente(URLModel url) {
		
		activateDeactivate(url);
		return uRLRepository.saveAndFlush(url);
	}
	
	
	public void activateDeactivate(URLModel url) {
		if(url.isEnabled()) {
			url.setEnabled(false);
		}else {
			url.setEnabled(true);
		}
	}
	
	
	public int desactivateExpiredURLs(Date oneMonthAgo) {
		return uRLRepository.desactivateExpiredURLs(oneMonthAgo);
	}
	
}
