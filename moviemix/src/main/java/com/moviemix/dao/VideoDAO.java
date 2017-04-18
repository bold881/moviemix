package com.moviemix.dao;

import java.util.List;

import com.moviemix.model.VideoLite;

public interface VideoDAO {
	
	public void addVideo(VideoLite v);
	
	public void updateVideo(VideoLite v);
	
	public List<VideoLite> listVideos();
	
	public List<VideoLite> listTodayVideos();
	
	public VideoLite getVideoById(int id);
	
	public void removeVideo(int id);
	
}
