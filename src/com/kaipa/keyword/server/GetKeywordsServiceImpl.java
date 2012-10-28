package com.kaipa.keyword.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.kaipa.keyword.client.GetKeywordsService;
import com.kaipa.keyword.shared.Keyword;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GetKeywordsServiceImpl extends RemoteServiceServlet implements
		GetKeywordsService {
	private static final Logger log = Logger.getLogger(GetKeywordsServiceImpl.class.getName()); 
	
	private static final Keyword KEYWORD_1 = new Keyword("kk", "http://www.kkey.in");
	private static final Keyword KEYWORD_2 = new Keyword("fb", "http://www.facebook.com");
	private static final Keyword KEYWORD_3 = new Keyword("m", "https://mail.google.com/mail/u/0/#inbox");
	private static final Keyword KEYWORD_4 = new Keyword("tm", "http://www.techmeme.com");
	private static final Keyword KEYWORD_5 = new Keyword("w", "http://www.wired.com");

	private static final List<Keyword> KEYWORDS = Lists.newArrayList(KEYWORD_1, KEYWORD_2, KEYWORD_3, KEYWORD_4, KEYWORD_5);
	
	@Override
	public List<Keyword> getKeywords() throws IllegalArgumentException {
		setupForFirstUse();
		Objectify ofy = ObjectifyService.begin();
		NamespaceManager.set(LoggedInUser.getUserId());
		Query<Keyword> query = ofy.query(Keyword.class).chunkSize(500);
		QueryResultIterator<Keyword> iterator = query.iterator();
		List<Keyword> keywords = new ArrayList<Keyword>();
		while(iterator.hasNext()) {
			Keyword keyword = iterator.next();
			keywords.add(keyword);
		}
		NamespaceManager.set("");
		return keywords;
	}

	@Override
	public Boolean doesKeyExistGlobally(String key) throws IllegalArgumentException {
		Keyword keyword = KeywordDatastore.findInGlobalAsWell(key);
		return keyword != null;
	}
	
	private void setupForFirstUse() {
		Keyword keyword = KeywordDatastore.findForUser("m");
		if (keyword == null) {
			log.info("Found nothing here. Adding keys");
			for (Keyword k : KEYWORDS) {
				KeywordDatastore.save(k);
			}
		} else {
			log.info("Nothing to do");
		}
	}
}
