package com.moviemix.service;

import java.util.List;

import com.moviemix.model.Video;
import com.moviemix.model.VideoLite;

public interface VideoService {
	
	public void addVideo(VideoLite v);
	
	public void updateVideo(VideoLite v);
	
	public List<Video> listVideos();
	
	public List<Video> listTodayVideos();
	
	public Video getVideoById(int id);
	
	public void removeVideo(int id);
}
