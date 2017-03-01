package com.moviemix.service;

import java.util.List;

import com.moviemix.model.Subscriber;

public interface SubscriberService {
	
	public void addSubscriber(Subscriber s);
	
	public List<Subscriber> getSubscribers();
	
	//public void removeSubscriber(Subscriber s);
}
