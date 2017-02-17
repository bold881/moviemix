package com.moviemix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.moviemix.service.VideoService;

@Controller
@RequestMapping("/")
public class AppController {
	
	@Autowired
	VideoService  videoService;
	
	@RequestMapping(value= {"/videos"}, method = RequestMethod.GET)
	public String listVideos(ModelMap model) {
		model.addAttribute("videos", this.videoService.listVideos());
		return "videos";
	}
	
	@RequestMapping(value= {"/videosofday"}, method = RequestMethod.GET)
	public String listVideosOfToday(ModelMap model) {
		model.addAttribute("videos", this.videoService.listTodayVideos());
		return "videosofday";
	}
}
