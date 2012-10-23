package com.kaipa.keyword.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kaipa.keyword.client.AddService;
import com.kaipa.keyword.shared.Keyword;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AddServiceImpl extends RemoteServiceServlet implements AddService {

	@Override
	public String add(String key, String url) throws IllegalArgumentException {
		key = key.toLowerCase();
		if (KeywordDatastore.findForUser(key) == null) {
			KeywordDatastore.save(new Keyword(key, url));
		}
		return "";
	}
}
