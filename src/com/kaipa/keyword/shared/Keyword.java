package com.kaipa.keyword.shared;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class Keyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	String keyword;
	String url;
	String search;
	long count;

	public Keyword() {
	}

	public Keyword(String keyword, String url, String search, long count) {
		super();
		this.keyword = keyword;
		this.url = url;
		this.search = search;
		this.count = count;
	}

	public Keyword(String keyword, String url) {
		super();
		this.keyword = keyword;
		this.url = url;
		this.search = null;
		this.count = 0;
	}

	public void incrementCount() {
		count++;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
