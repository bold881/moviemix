package com.moviemix.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="videolite")
public class Video {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@NotNull
	@Column(name="downloadurl")
	private String downloadurl;
	
	@Column(name="created")
	private Date created;
	
	@Column(name="domain")
	private String domain;
	
	@Column(name="pageurl")
	private String pageurl;
	
	@Column(name="info")
	private String info;
	
	public Video() {};
	
	public Video(Long id, String title, String downloadurl, 
			Date created, String domain, String pageurl, String info) {
		this.id = id;
		this.title = title;
		this.downloadurl = downloadurl;
		this.created = created;
		this.domain = domain;
		this.pageurl = pageurl;
		this.info = info;
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
