package com.openwt.urlshortener.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.openwt.urlshortener.service.URLService;

@Component
public class DesactivateExpiredURLScheduledTask {
	
	private static final Logger logger = LoggerFactory.getLogger(DesactivateExpiredURLScheduledTask.class);

    @Autowired
    private URLService uRLService;
    
	
	@Scheduled(fixedRateString  = "${spring.scheduled.fixed.rate}", initialDelayString = "${spring.scheduled.initial.delay}")
	@Transactional
	public void desactivateExpiredDates() {
		Date oneMonthAgo = Date.from(LocalDateTime.now().minusMonths(1).atZone(ZoneId.of("Europe/Paris")).toInstant());
		
		int updatedURLs = uRLService.desactivateExpiredURLs(oneMonthAgo);
		
		logger.info("Desactivated {} expired URLs", updatedURLs);
	}
	
}
