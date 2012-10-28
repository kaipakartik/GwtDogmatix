package com.kaipa.keyword.server;

import org.apache.commons.validator.routines.UrlValidator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kaipa.keyword.client.AddService;
import com.kaipa.keyword.shared.FieldVerifier;
import com.kaipa.keyword.shared.Keyword;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AddServiceImpl extends RemoteServiceServlet implements AddService {

	@Override
	public Boolean add(String key, String url) throws IllegalArgumentException {
		UrlValidator urlValidator = new UrlValidator();
		key = key.toLowerCase();
		if (KeywordDatastore.findForUser(key) == null && urlValidator.isValid(url) && FieldVerifier.isValidKey(key)) {
			KeywordDatastore.save(new Keyword(key, url));
			return true;
		}
		return false;
	}
}
