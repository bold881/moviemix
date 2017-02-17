package com.moviemix.dao;

import java.util.List;

import com.moviemix.model.Video;

public interface VideoDAO {
	
	public void addVideo(Video v);
	
	public void updateVideo(Video v);
	
	public List<Video> listVideos();
	
	public List<Video> listTodayVideos();
	
	public Video getVideoById(int id);
	
	public void removeVideo(int id);
	
}
