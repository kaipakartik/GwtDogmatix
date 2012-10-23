package com.kaipa.keyword.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kaipa.keyword.client.DeleteService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DeleteServiceImpl extends RemoteServiceServlet implements
		DeleteService {

	@Override
	public String delete(String key, String url) throws IllegalArgumentException {
		KeywordDatastore.delete(key);
		return key + "delete";
	}
}
