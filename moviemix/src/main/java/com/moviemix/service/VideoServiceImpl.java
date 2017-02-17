package com.moviemix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviemix.dao.VideoDAO;
import com.moviemix.model.Video;

@Service
public class VideoServiceImpl implements VideoService {
	
	private VideoDAO videoDAO;
	
	@Autowired
	public void setVideoDAO(VideoDAO videoDAO) {
		this.videoDAO = videoDAO;
	}

	@Override
	@Transactional
	public void addVideo(Video v) {
		this.videoDAO.addVideo(v);
	}

	@Override
	@Transactional
	public void updateVideo(Video v) {
		this.videoDAO.updateVideo(v);
	}

	@Override
	@Transactional
	public List<Video> listVideos() {
		return this.videoDAO.listVideos();
	}
	
	@Override
	public List<Video> listTodayVideos() {
		return this.videoDAO.listTodayVideos();
	}

	@Override
	@Transactional
	public Video getVideoById(int id) {
		return this.videoDAO.getVideoById(id);
	}

	@Override
	@Transactional
	public void removeVideo(int id) {
		this.videoDAO.removeVideo(id);
	}

}
