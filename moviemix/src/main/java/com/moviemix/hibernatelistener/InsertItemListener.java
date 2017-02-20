package com.moviemix.hibernatelistener;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InsertItemListener implements PostInsertEventListener{

	private static final long serialVersionUID = 5902147771442167289L;
	
	private static  final Logger logger = 
			LoggerFactory.getLogger(InsertItemListener.class);

	@Override
	public void onPostInsert(PostInsertEvent event) {
		
		logger.info("-----------> some kind insert");
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
