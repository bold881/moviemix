package com.moviemix.scheduledtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moviemix.service.MovieMixEmailService;
import com.moviemix.service.SubscriberService;
import com.moviemix.service.VideoService;

@Component
public class VideosOfTodayCheck {
	
	@Autowired
	MovieMixEmailService movieMixEmailService;
	
	@Autowired
	VideoService videoService;
	
	@Autowired
	SubscriberService subscriberService;
	
    //private static final Logger log = LoggerFactory.getLogger(VideosOfTodayCheck.class);

    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(cron="0 0 20 * * ?")
    public void reportCurrentTime() {
    	this.movieMixEmailService.sendFreshVideoEmail(this.subscriberService.getSubscribers(), 
    			this.videoService.listTodayVideos());
    }
	
    @Scheduled(cron="0 0 20 * * ?")
    public void reportRichCurrentTime() {
    	this.movieMixEmailService.sendRichFreshVideoEmail(this.subscriberService.getSubscribers(), 
    			this.videoService.listTodayVideos());
    }
}
