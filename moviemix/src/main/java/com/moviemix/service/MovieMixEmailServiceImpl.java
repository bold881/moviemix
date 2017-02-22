package com.moviemix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

import com.moviemix.model.Video;

@Service
public class MovieMixEmailServiceImpl implements MovieMixEmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendFreshVideoEmail(String to, List<Video> lstVideos) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("970778418@qq.com");
		message.setTo(to);
		message.setSubject("New test from Dai Feng");
		message.setText("Hell from videomix.info");
		mailSender.send(message);
		
	}

}
