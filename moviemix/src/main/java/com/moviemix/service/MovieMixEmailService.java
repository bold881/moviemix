package com.moviemix.service;

import java.util.List;

import com.moviemix.model.Subscriber;
import com.moviemix.model.Video;

public interface MovieMixEmailService {
	
	public abstract void sendFreshVideoEmail(String to, List<Video> lstVideos);
	
	public abstract void sendFreshVideoEmail(List<Subscriber> lstSubscribers, List<Video> lstVideos);
}
