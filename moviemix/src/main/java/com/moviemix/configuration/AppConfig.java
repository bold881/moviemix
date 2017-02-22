package com.moviemix.configuration;

import java.security.GeneralSecurityException;
import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.moviemix.dao.VideoDAO;
import com.moviemix.dao.VideoDAOImpl;
import com.moviemix.service.VideoService;
import com.moviemix.service.VideoServiceImpl;
import com.sun.mail.util.MailSSLSocketFactory;

@Configuration
@EnableAsync
@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = "com.moviemix")
@PropertySource({"classpath:log4j.properties", "classpath:mail.properties"})
public class AppConfig {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	
	@Bean
	public VideoDAO videoDAO() {
		return new VideoDAOImpl();
	}
	
	@Bean
	public VideoService videoService() {
		return new VideoServiceImpl();
	}
	
	@Bean
	public JavaMailSenderImpl mailSender(Environment env) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("mailserver.host"));
	    mailSender.setUsername(env.getProperty("mailserver.username"));
	    mailSender.setPassword(env.getProperty("mailserver.password"));
	    Properties javaMailProperties = new Properties();
	    javaMailProperties.setProperty("mail.debug", "true");
	    javaMailProperties.setProperty("mail.smtp.auth", "true");
	    javaMailProperties.setProperty("mail.host", "smtp.qq.com");
	    javaMailProperties.setProperty("mail.transport.protocol", "smtp");
	    
	    MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
		    sf.setTrustAllHosts(true);
		    javaMailProperties.put("mail.smtp.ssl.enable", "true");
		    javaMailProperties.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	    mailSender.setJavaMailProperties(javaMailProperties);
	    return mailSender;
	}
}
