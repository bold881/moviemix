package com.moviemix.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moviemix.model.Video;
import com.moviemix.model.VideoLite;


@Repository
@Transactional
public class VideoDAOImpl implements VideoDAO {
	
	private static  final Logger logger = 
			LoggerFactory.getLogger(VideoDAOImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addVideo(VideoLite v) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(v);
		logger.info("Video saved successfully, Video Details="+v);
	}

	@Override
	public void updateVideo(VideoLite v) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(v);
		logger.info("Video updated successfully, Video Details="+v);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> listVideos() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Video> videoList = session.createQuery("from Video").list();
		for(Video v : videoList){
			logger.info("Person List::" + v);
		}
		return videoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Video> listTodayVideos() {
		Session session = this.sessionFactory.getCurrentSession();
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String str = "from Video v where day(v.created) = :date and month(v.created) = :month and year(v.created) = :year";
        Query query = session.createQuery(str);
        query.setInteger("date", cal.get(Calendar.DATE));
        query.setInteger("month", cal.get(Calendar.MONTH)+1);
        query.setInteger("year", cal.get(Calendar.YEAR));

		List<Video> videoList = query.list();
		for(Video v : videoList){
			logger.info("Person List::" + v);
		}
		return videoList;
	}

	@Override
	public Video getVideoById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Video v = (Video)session.load(Video.class, new Long(id));
		logger.info("Video loaded successfully, Video details="+v);
		return v;
	}

	@Override
	public void removeVideo(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		VideoLite v = (VideoLite)session.load(VideoLite.class, new Long(id));
		
		if(null != v) {
			session.delete(v);
		}
		logger.info("Video deleted successfully, video details="+v);
	}

}
