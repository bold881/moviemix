package com.moviemix.service;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemix.dao.SubscriberDAOImpl;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	
	@Autowired
	private TransportClient transportClient;
	
	private static  final Logger logger = 
			LoggerFactory.getLogger(ElasticsearchServiceImpl.class);

	@Override
	public String getClusterName() {
		ClusterHealthResponse healths = transportClient.admin().cluster().prepareHealth().get();
		String clusterName = healths.getClusterName();
		logger.info(clusterName);
		return clusterName;
	}

}
