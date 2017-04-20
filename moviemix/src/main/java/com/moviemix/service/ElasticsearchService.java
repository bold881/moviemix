package com.moviemix.service;

import java.util.List;

import com.moviemix.model.EsVideo;

public interface ElasticsearchService {
	public String getClusterName();
	
	public List<EsVideo> doSearch(String keyWord);
}
