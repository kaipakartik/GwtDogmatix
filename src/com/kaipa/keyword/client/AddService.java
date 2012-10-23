package com.kaipa.keyword.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("add")
public interface AddService extends RemoteService {
	String add(String key, String url) throws IllegalArgumentException;
}
