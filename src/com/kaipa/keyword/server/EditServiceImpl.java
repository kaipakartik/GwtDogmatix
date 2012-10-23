package com.kaipa.keyword.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kaipa.keyword.client.EditService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EditServiceImpl extends RemoteServiceServlet implements
		EditService {

	@Override
	public String edit(String key, String url) throws IllegalArgumentException {
		return key + "edit";
	}
}
