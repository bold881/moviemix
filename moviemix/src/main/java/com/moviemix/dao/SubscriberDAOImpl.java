package com.moviemix.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviemix.model.Subscriber;

@Repository
@Transactional
public class SubscriberDAOImpl implements SubscriberDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static  final Logger logger = 
			LoggerFactory.getLogger(SubscriberDAOImpl.class);
	
	@Override
	public void addSubscriber(Subscriber s) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(s);
		logger.info("Subscriber saved successfully, details:" + s);
	}

	@Override
	@SuppressWarnings(value = { "unchecked" })
	public List<Subscriber> getSubscribers() {
		Session session = sessionFactory.getCurrentSession();
		List<Subscriber> lstSubscriber = session.createQuery("from Subscriber").list();
		for(Subscriber suber: lstSubscriber) {
			logger.info("Subscribe List: " + suber);
		}
		return lstSubscriber;
	}

}
