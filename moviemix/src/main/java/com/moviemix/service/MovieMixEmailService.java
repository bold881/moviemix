package com.moviemix.service;

import java.util.List;

import com.moviemix.model.Subscriber;
import com.moviemix.model.VideoLite;

public interface MovieMixEmailService {
	
	public abstract void sendFreshVideoEmail(String to, List<VideoLite> lstVideos);
	
	public abstract void sendFreshVideoEmail(List<Subscriber> lstSubscribers, List<VideoLite> lstVideos);
	
	public abstract void sendRichFreshVideoEmail(List<Subscriber> lstSubscribers, List<VideoLite> lstVideos);
}
