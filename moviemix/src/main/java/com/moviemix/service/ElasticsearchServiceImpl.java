package com.moviemix.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviemix.model.EsVideo;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	
	private static final String INDEXNAME = "moviemix";
	
	private static final int SCROLL_KEEP_ALIVETIME = 30000;
	
	private static final String FIELD_POSTERURL = "posterurl";
	
	private static final String FIELD_TITLEE = "title";
	
	private static final String FIELD_RELEASEDATE = "releasedate";
	
	private static final String FIELD_COUNTRY = "country";
	
	private static final String FIELD_GENRE = "genre";
	
	private static final String FIELD_LANGUAGE = "language";
	
	private static final String FIELD_SUBTITLE = "subtitle";
	
	private static final String FIELD_IMDBSCORE = "imdbscore";
	
	private static final String FIELD_VIDEOFORMAT = "videoformat";
	
	private static final String FIELD_VIDEOSIZE = "videosize";
	
	private static final String FIELD_FILESIZE = "filesize";
	
	private static final String FIELD_RUNTIME = "runtime";
	
	private static final String FIELD_DIRECTOR = "director";
	
	private static final String FIELD_CAST = "cast";
	
	private static final String FIELD_STORYLINE = "storyline";
	
	private static final String FIELD_AWARDS = "awards";
	
	private static final String FIELD_DOWNLOADURL = "downloadurl";
	
	private static final String FIELD_CREATED = "created";
	
	private static final String FIELD_DOMAIN = "domain";
	
	private static final String FIELD_PAGEURL = "pageurl";
	
	private static final int PAGE_SIZE = 100; 
	
	private static int pageLimit = 3;
	
	
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

	@Override
	public List<EsVideo> doSearch(String keyWord) {
		if(keyWord == null || keyWord.isEmpty())
			return null;
		
		SearchResponse searchResponse = null;
		
		searchResponse = transportClient.prepareSearch(INDEXNAME)
				.setScroll(new TimeValue(SCROLL_KEEP_ALIVETIME))
				.setQuery(QueryBuilders.multiMatchQuery(keyWord, FIELD_TITLEE,
						FIELD_RELEASEDATE, FIELD_COUNTRY, FIELD_GENRE,
						FIELD_LANGUAGE, FIELD_SUBTITLE, FIELD_IMDBSCORE,
						FIELD_DIRECTOR, FIELD_CAST, FIELD_STORYLINE,
						FIELD_AWARDS, FIELD_DOMAIN))
				.highlighter(new HighlightBuilder()
						.field(new Field(FIELD_TITLEE))
						.field(new Field(FIELD_RELEASEDATE))
						.field(new Field(FIELD_COUNTRY))
						.field(new Field(FIELD_GENRE))
						.field(new Field(FIELD_LANGUAGE))
						.field(new Field(FIELD_SUBTITLE))
						.field(new Field(FIELD_DIRECTOR))
						.field(new Field(FIELD_CAST))
						.field(new Field(FIELD_STORYLINE))
						.field(new Field(FIELD_AWARDS))
						.field(new Field(FIELD_DOMAIN)))
				.setFetchSource(new String[]{FIELD_TITLEE, FIELD_DOWNLOADURL}, null)
				.setSize(PAGE_SIZE)
				.get();
		
		List<EsVideo> esVideos = new ArrayList<EsVideo>();
		
		do {
			for(SearchHit hit: searchResponse.getHits().getHits()) {
				EsVideo esVideo = new EsVideo();
				esVideo.setScore(hit.getScore());
				esVideo.setId(hit.getId());
				esVideo.setType(hit.getType());
				
				Map<String, Object> mapSources = hit.getSource();
				esVideo.setTitle((String)mapSources.get(FIELD_TITLEE));
				esVideo.setDownloadUrl((String)mapSources.get(FIELD_DOWNLOADURL));
				esVideo.setHighlightFields(hit.getHighlightFields());
				
				esVideos.add(esVideo);
			}
			pageLimit--;
			searchResponse = transportClient.prepareSearchScroll(searchResponse.getScrollId())
					.setScroll(new TimeValue(SCROLL_KEEP_ALIVETIME))
					.execute()
					.actionGet();
		
		} while(searchResponse.getHits().getHits().length != 0 && pageLimit > 0);
		
		return esVideos;
	}

	
}
