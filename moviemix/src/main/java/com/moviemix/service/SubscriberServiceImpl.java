package com.moviemix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemix.dao.SubscriberDAO;
import com.moviemix.model.Subscriber;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	private SubscriberDAO subscriberDAO;
	
	@Override
	public void addSubscriber(Subscriber s) {
		this.subscriberDAO.addSubscriber(s);
	}

	@Override
	public List<Subscriber> getSubscribers() {
		return this.subscriberDAO.getSubscribers();
	}

}
