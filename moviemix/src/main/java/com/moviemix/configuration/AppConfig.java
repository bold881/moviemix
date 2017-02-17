package com.moviemix.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.moviemix.dao.VideoDAO;
import com.moviemix.dao.VideoDAOImpl;
import com.moviemix.service.VideoService;
import com.moviemix.service.VideoServiceImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.moviemix")
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
}
