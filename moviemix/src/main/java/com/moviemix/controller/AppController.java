package com.moviemix.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.moviemix.model.Subscriber;
import com.moviemix.service.SubscriberService;
import com.moviemix.service.VideoService;

@Controller
@RequestMapping("/")
public class AppController {
	
	@Autowired
	VideoService  videoService;
	
	@Autowired
	SubscriberService subscriberService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getHome(ModelMap model) {
		return "home";
	}
	
	
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
	
	@RequestMapping(value="/subscribe", method=RequestMethod.GET)
	public String subscribeEmail() {
		return "subscribe";
	}
	
	@RequestMapping(value="/subscribeok", method=RequestMethod.GET)
	public String subscribeOK() {
		return "subscribeok";
	}
	
	@RequestMapping(value="/subscribe", method=RequestMethod.POST)
	public String processSubscribe(
			@Valid Subscriber subscriber,
			Errors error) {
		if(error.hasErrors()) {
			return "subscribe";
		}
		
		subscriber.setCreated(new Date());
		this.subscriberService.addSubscriber(subscriber);
		return "redirect:/subscribeok";
		
	}
}
