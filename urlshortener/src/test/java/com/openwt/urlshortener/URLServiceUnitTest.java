package com.openwt.urlshortener;

import java.util.Calendar;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.openwt.urlshortener.model.URLModel;
import com.openwt.urlshortener.repository.URLRepository;
import com.openwt.urlshortener.service.URLService;


class URLServiceUnitTest {

	@Spy
    @InjectMocks
	private URLService uRLService = new URLService();
	
	@Mock
	private URLRepository uRLRepository;
	
	private URLModel urlModel;
	
	
	@BeforeEach
    public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		
		String longUrl = "https://developer.apple.com/design/human-interface-guidelines/ios/user-interaction/audio/";
		String shortUrl = "https://tinyurl.com/qs8b9bp";
		urlModel = new URLModel(longUrl, shortUrl, Calendar.getInstance(Locale.FRANCE).getTime());
		
		Mockito.doReturn(urlModel).when(uRLRepository).saveAndFlush(Mockito.any(URLModel.class));
	}
	
	@Test
	void changeEnablementeUnitTest() {
		uRLService.changeEnablemente(urlModel);
		Assertions.assertEquals(false, urlModel.isEnabled());
	}

}
