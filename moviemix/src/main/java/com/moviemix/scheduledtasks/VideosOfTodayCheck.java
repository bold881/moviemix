package com.moviemix.scheduledtasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moviemix.service.MovieMixEmailService;

@Component
public class VideosOfTodayCheck {
	
	@Autowired
	MovieMixEmailService movieMixEmailService;
	
    //private static final Logger log = LoggerFactory.getLogger(VideosOfTodayCheck.class);

    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
    	this.movieMixEmailService.sendFreshVideoEmail("2243675357@qq.com", null);
    	//log.info("The time is now {}", dateFormat.format(new Date()));
    }
	
}
