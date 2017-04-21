package com.moviemix.dao;

import java.util.List;

import com.moviemix.model.Video;
import com.moviemix.model.VideoLite;

public interface VideoDAO {
	
	public void addVideo(VideoLite v);
	
	public void updateVideo(VideoLite v);
	
	public List<Video> listVideos();
	
	public List<Video> listTodayVideos();
	
	public Video getVideoById(int id);
	
	public void removeVideo(int id);
	
}
