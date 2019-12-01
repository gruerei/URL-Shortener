package com.openwt.urlshortener.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openwt.urlshortener.dto.ErrorDTO;
import com.openwt.urlshortener.dto.URLOutDTO;
import com.openwt.urlshortener.exception.CustomException;
import com.openwt.urlshortener.mapper.URLMapper;
import com.openwt.urlshortener.model.URLModel;
import com.openwt.urlshortener.service.URLService;
import com.openwt.urlshortener.utils.URLUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/")
public class URLController {
	

	
	private static final Logger logger = LoggerFactory.getLogger(URLController.class);
	
	@Autowired
	URLService urlService;
	
	@Autowired
	URLMapper urlMapper;
	

	@ApiOperation(produces = "application/json", value = "Enter an URL to be shortened by the Wonderful Tiny App!!")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid Syntax for Client Request.", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
	@PostMapping(value = "/urlshortener", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<URLOutDTO> createURL(
			@ApiParam(value = "Input URL foto be shortened") @RequestParam(value="urlInput", required = true) String urlInput) throws CustomException {
		
		logger.info("Endpoint POST called -> /urlshortener");
		
		boolean urlExists = URLUtils.validateURL(urlInput);
		
		if(urlExists) {
			
			String shortenedLink = URLUtils.giveMeShortURLVersion(URLUtils.REST_SHORTENER_URI+"?url="+urlInput);
			
			URLModel url = urlService.createURL(urlInput, shortenedLink);
			
			URLOutDTO urlDto = urlMapper.entityToDto(url);
			
			return new ResponseEntity<>(urlDto, HttpStatus.OK);
		}else {
			//You should never get here
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
	}
	

	@ApiOperation(produces = "application/json", value = "Returns all created URLs")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid Syntax for Client Request.", response = ErrorDTO.class),
			@ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
	@GetMapping(value = "/urlshortener", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<URLOutDTO>> returnAllURLs() {
		
		logger.info("Endpoint GET called -> /urlshortener");
		
		List<URLModel> urls = urlService.getAllOrderedByDateCreation();
		
		List<URLOutDTO> urlsDto = urls.stream()
		.map(url -> urlMapper.entityToDto(url))
		.collect(Collectors.toList());
		
		return new ResponseEntity<>(urlsDto, HttpStatus.OK);
	}
	
	@ApiOperation(produces = "application/json", value = "Deletion of URLs")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Action queued.", response = ErrorDTO.class),
			@ApiResponse(code = 204, message = "Action has been performed but the response did not include an entity.", response = ErrorDTO.class)}
	)
	@DeleteMapping(value = "/urlshortener/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteURL(
			@ApiParam(value = "Id indicating the URL to be deleted") @RequestParam(value="idURL", required = true) long idURL) throws CustomException {
		
		logger.info("Endpoint DELETE called -> /urlshortener/{id}");
		
		String msg;
		if(null != urlService.getById(idURL)) {
			urlService.deleteURL(idURL);
			msg = "URL with id: "+idURL+" has been deleted.";
			logger.info(msg);
		}else {
			msg = "No URL with id: "+idURL+" found to be deleted";
			logger.error(msg);
			throw new CustomException("NOT FOUND URL",
					msg);
		}
		
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@ApiOperation(produces = "application/json", value = "Activation or Inactivation of URLs")
	@ApiResponses(value = {})
	@PutMapping(value = "/urlshortener/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<URLOutDTO> modifyEnablement(
			@ApiParam(value = "Id indicating the URL to be activated or desactivated") @RequestParam(value="idURL", required = true) long idURL) throws CustomException {
		
		logger.info("Endpoint PUT called -> /urlshortener/{id}");
		
		URLModel url = urlService.getById(idURL);
		
		if(null != urlService.getById(idURL)) {
			url = urlService.changeEnablemente(url);
		}else {
			String msg = "No URL with id: "+idURL+" found to be activated/desactivated";
			logger.error(msg);
			throw new CustomException("NOT FOUND URL",
					msg);
		}
		
		URLOutDTO urlDto = urlMapper.entityToDto(url);
		
		return new ResponseEntity<>(urlDto, HttpStatus.OK);
	}
}
