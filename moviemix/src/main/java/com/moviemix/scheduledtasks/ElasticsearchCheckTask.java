package com.moviemix.scheduledtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.moviemix.service.ElasticsearchService;

@Component
public class ElasticsearchCheckTask {

	@Autowired
	private ElasticsearchService elasticsearchService;
	
	//@Scheduled(fixedRate=5000)
	public void getClusterStatus() {
		this.elasticsearchService.getClusterName();
	}
}
