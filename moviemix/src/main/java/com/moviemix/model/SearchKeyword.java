package com.moviemix.model;

import javax.validation.constraints.NotNull;

public class SearchKeyword {
	
	@NotNull
	private String keyword;
	
	public SearchKeyword() {
	};
	
	public SearchKeyword(String keyword) {
		this.keyword = keyword;
	};

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
