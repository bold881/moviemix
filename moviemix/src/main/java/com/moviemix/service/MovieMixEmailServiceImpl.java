package com.moviemix.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.moviemix.dao.SubscriberDAOImpl;
import com.moviemix.model.Subscriber;
import com.moviemix.model.Video;

@Service
@PropertySource({"classpath:log4j.properties", "classpath:mail.properties"})
public class MovieMixEmailServiceImpl implements MovieMixEmailService {
	
	@Autowired
	Environment env;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine thymeleaf;
	
	private static  final Logger logger = 
			LoggerFactory.getLogger(MovieMixEmailServiceImpl.class);
	
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
	public void sendRichFreshVideoEmail(List<Subscriber> lstSubscribers, List<Video> lstVideos) {
		if(lstVideos.isEmpty() || lstSubscribers.isEmpty())
			return;
		String fromAddr = env.getProperty("mailserver.from");
		
		SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd'-Fresh Videos of Today!'");
		String mailSubject = sft.format(new Date());
		
		for(Subscriber s : lstSubscribers) {
			MimeMessage message = mailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom(fromAddr);
				helper.setTo(s.getEmail());
				//helper.setTo("2243675357@qq.com");
				helper.setSubject(mailSubject);
				Context ctx = new Context();
				ctx.setVariable("subscriber", s);
				logger.info(s.getNickname());
				ctx.setVariable("lstVideos", lstVideos);
				String emailText = thymeleaf.process("emailFreshVideos.html", ctx);
				helper.setText(emailText, true);
				mailSender.send(message);
				//break;
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
