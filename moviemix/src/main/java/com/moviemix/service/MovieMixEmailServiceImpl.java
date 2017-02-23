package com.moviemix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.moviemix.model.Subscriber;
import com.moviemix.model.Video;

@Service
@PropertySource({"classpath:log4j.properties", "classpath:mail.properties"})
public class MovieMixEmailServiceImpl implements MovieMixEmailService {
	
	@Autowired
	Environment env;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void sendFreshVideoEmail(List<Subscriber> lstSubscribers, List<Video> lstVideos) {
		if(lstVideos.isEmpty() || lstSubscribers.isEmpty())
			return;
		for(Subscriber s : lstSubscribers) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(env.getProperty("mailserver.from"));
			message.setTo(s.getEmail());
			message.setSubject("New Videos from VideoMix");
			String mailContext = "";
			for(Video video:lstVideos){
				mailContext += video.toString();
			}
			message.setText(mailContext);
			mailSender.send(message);
		}
		
	}

	@Override
	public void sendFreshVideoEmail(String to, List<Video> lstVideos) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(env.getProperty("mailserver.from"));
		message.setTo(to);
		message.setSubject("New test from Dai Feng");
		message.setText("Hell from videomix.info");
		mailSender.send(message);
		
	}

}
