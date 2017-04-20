package com.moviemix.model;

import java.util.Map;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

public class EsVideo {
	
	private String title;
	
	private String downloadUrl;
	
	private Map<String, HighlightField> highlightFields;
	
	private float score;
	
	private String id;
	
	private String type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Map<String, HighlightField> getHighlightFields() {
		return highlightFields;
	}
	
	public String getHightlightFields() {
		String hintHighlight = "";
		for(Map.Entry<String, HighlightField> entry: highlightFields.entrySet()) {
			Text[] fragments = entry.getValue().getFragments();
			for(Text frag : fragments) {
				hintHighlight += frag.toString();
			}
		}
		
		return hintHighlight;
	}

	public void setHighlightFields(Map<String, HighlightField> highlightFields) {
		this.highlightFields = highlightFields;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float source) {
		this.score = source;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
