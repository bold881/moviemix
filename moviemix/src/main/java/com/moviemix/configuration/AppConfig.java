package com.moviemix.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
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
public class AppConfig extends WebMvcConfigurerAdapter 
implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	public AppConfig() {
		super();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext appContext) 
			throws BeansException {
		this.applicationContext = appContext;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = 
				new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(this.applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCacheable(true);
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
	  SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	  templateEngine.setTemplateResolver(templateResolver());
	  templateEngine.setEnableSpringELCompiler(true);
	  return templateEngine;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/");
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
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
	
	@SuppressWarnings("resource")
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
}
