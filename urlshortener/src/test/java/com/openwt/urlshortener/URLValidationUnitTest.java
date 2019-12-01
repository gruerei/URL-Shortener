package com.openwt.urlshortener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.openwt.urlshortener.exception.CustomException;
import com.openwt.urlshortener.utils.URLUtils;


class URLValidationUnitTest {
	
	private String LONG_URL_OK = "https://developer.apple.com/design/human-interface-guidelines/ios/user-interaction/audio/";
	private String SHORT_URL_OK = "https://tinyurl.com/qs8b9bp";
	
	
	@Test
	void urlValidationTest() throws CustomException {
		boolean urlOK = URLUtils.validateURL(LONG_URL_OK);
		Assertions.assertEquals(true, urlOK);
	}
	
	@Test
	void urlShortenerTest() throws CustomException {
		String shortUrl = URLUtils.giveMeShortURLVersion(URLUtils.REST_SHORTENER_URI+"?url="+LONG_URL_OK);
		Assertions.assertEquals(SHORT_URL_OK, shortUrl);
	}

}
