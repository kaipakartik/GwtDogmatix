package com.kaipa.keyword.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("edit")
public interface EditService extends RemoteService {
	String edit(String key, String url) throws IllegalArgumentException;
}
