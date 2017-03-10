package com.moviemix.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
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
public class AppConfig extends WebMvcConfigurerAdapter{
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		return viewResolver;
	}
	
	@Bean
	public ViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setOrder(3);
		return viewResolver;
	}
	
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
	  SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	  templateEngine.setTemplateResolver(templateResolver);
	  return templateEngine;
	}
	
	@Bean
	public ServletContextTemplateResolver templateResolver(ServletContext servletContext) {
		ServletContextTemplateResolver templateResolver = 
				new ServletContextTemplateResolver(servletContext);
		templateResolver.setPrefix("/WEB-INF/views/mail/");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		return templateResolver;
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
	
	@Bean
	public TransportClient transportClient(Environment env) {
		Settings settings = Settings.builder()
				.put("cluster.name", env.getProperty("elasticsearch.cluster_name")).build();
		TransportClient client;
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(
							new InetSocketTransportAddress(
									InetAddress.getByName(env.getProperty("elasticsearch.host")), 
									Integer.parseInt(env.getProperty("elasticsearch.port"))));
			return client;
		} catch (NumberFormatException | UnknownHostException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
	}
}
