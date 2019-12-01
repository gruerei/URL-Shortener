package com.openwt.urlshortener.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.openwt.urlshortener.exception.CustomException;

public class URLUtils {
	
	public static final String REST_SHORTENER_URI  = "https://tinyurl.com/api-create.php";
	
	private static final Logger logger = LoggerFactory.getLogger(URLUtils.class);
	
	public static boolean validateURL(String urlInput) throws CustomException {
		
		logger.info("Validating input URL {}", urlInput);
		
		try {
			URL url = new URL(urlInput);
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			int responseCode = huc.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				logger.info("Nice, {} EXISTS!!", urlInput);
				return true;
			}
			
		} catch (IOException e) {
			logger.error("URL VALIDATION ERROR: {} ", e.toString());
			throw new CustomException("URL VALIDATION ERROR", e.toString());
		}
		  
		return false;
	}
	
	public static String giveMeShortURLVersion(String urlRequest) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(urlRequest);
		
		Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_VALUE);
		Response response  = invocationBuilder.get();
		
		logger.info("Calling Tyny App Service...");
		String shortenedLink = response.readEntity(String.class);
		
		logger.info("Shortened link OBTAINED!! -> {}",shortenedLink);
		
		return shortenedLink;
	}
}
