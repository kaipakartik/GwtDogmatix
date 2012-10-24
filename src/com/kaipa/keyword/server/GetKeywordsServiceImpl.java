package com.kaipa.keyword.server;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.QueryResultIterator;
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

	@Override
	public List<Keyword> getKeywords() throws IllegalArgumentException {
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
	public Boolean doesKeyExist(String key) throws IllegalArgumentException {
		Keyword keyword = KeywordDatastore.findForUser(key);
		return keyword != null;
	}
}
