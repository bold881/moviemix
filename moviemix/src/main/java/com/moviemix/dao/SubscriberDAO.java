package com.moviemix.dao;

import java.util.List;

import com.moviemix.model.Subscriber;

public interface SubscriberDAO {
	
	public void addSubscriber(Subscriber s);
	
	public List<Subscriber> getSubscribers();
}
