package com.kaipa.keyword.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kaipa.keyword.shared.Keyword;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("get")
public interface GetKeywordsService extends RemoteService {
	List<Keyword> getKeywords() throws IllegalArgumentException;
	Boolean doesKeyExistGlobally(String key) throws IllegalArgumentException;
}
