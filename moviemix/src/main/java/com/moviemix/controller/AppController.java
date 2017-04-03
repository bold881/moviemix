package com.moviemix.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.moviemix.model.SearchKeyword;
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
	public String subscribeEmail(ModelMap model) {
		model.addAttribute(new Subscriber());
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
	
	@ExceptionHandler({SQLException.class,DataAccessException.class})
	public String databaseError() {
		return "error";
	}
	
	@GetMapping("/search")
	public String processSearch(ModelMap model) {
		model.addAttribute(new SearchKeyword());
		return "search";
	}
	
	@PostMapping("/search")
	public String doSearch(@ModelAttribute SearchKeyword searchKeyword) {
		return "search";
	}
}
