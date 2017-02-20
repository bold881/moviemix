package com.moviemix.hibernatelistener;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.moviemix.hibernatelistener.InsertItemListener;

@Component
public class HibernateListenerConfigurer {
	
	@Autowired
	private  SessionFactory sessionFactory;

	@Autowired
	private InsertItemListener listener;

	
	@PostConstruct
	public void registerListener() {
		EventListenerRegistry registry = ((SessionFactoryImpl)sessionFactory).getServiceRegistry().getService(
				EventListenerRegistry.class);
		
		registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(listener);
	}
}
