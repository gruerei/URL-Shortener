package com.openwt.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;

import com.openwt.urlshortener.controller.URLController;
import com.openwt.urlshortener.model.URLModel;
import com.openwt.urlshortener.service.URLService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UrlshortenerApplicationTests {

	//@Autowired
	//private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private URLService uRLService;
	
	@Autowired
	private URLController controller;
	
	private static URLModel urlModel;
	
	
	@BeforeAll
    public static void setUp() {
		String longUrl = "https://developer.apple.com/design/human-interface-guidelines/ios/user-interaction/audio/";
		String shortUrl = "https://tinyurl.com/qs8b9bp";
		urlModel = new URLModel(longUrl, shortUrl, Calendar.getInstance(Locale.FRANCE).getTime());
	}
	
	@Test
	@Order(1)  
	public void contextLoads() {
		  assertThat(controller).isNotNull();
	}
	
	@Test
	@WithMockUser(username = "GRR")
	@Order(2)  
	public void integrationTests() throws Exception {
		/*mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();*/
		
		//mockMvc.perform(formLogin("/auth").user("GRR").password("1234"));
		
		mockMvc.perform(post("/urlshortener")
				.param("urlInput", urlModel.getLongUrl())
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content()
	            	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		
		URLModel urlfromDB = uRLService.findOneByLongURL(urlModel.getLongUrl());
		assertEquals(urlModel.getLongUrl(), urlfromDB.getLongUrl());
		assertEquals(urlModel.getShortUrl(), urlfromDB.getShortUrl());
		

		mockMvc.perform(put("/urlshortener/{id}",urlfromDB.getId())
				.param("idURL", String.valueOf(urlfromDB.getId()))
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content()
	            	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		
		urlfromDB = uRLService.findOneByLongURL(urlModel.getLongUrl());
		assertEquals(false, urlfromDB.isEnabled());
		
		mockMvc.perform(delete("/urlshortener/{id}",urlfromDB.getId())
				.param("idURL", String.valueOf(urlfromDB.getId()))
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content()
	            	      .contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE));
		
		urlfromDB = uRLService.findOneByLongURL(urlModel.getLongUrl());
		assertNull(urlfromDB);
		
	}

}
