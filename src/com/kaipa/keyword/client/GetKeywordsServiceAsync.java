package com.kaipa.keyword.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kaipa.keyword.shared.Keyword;

public interface GetKeywordsServiceAsync {

	void getKeywords(AsyncCallback<List<Keyword>> callback);

	void doesKeyExist(String key, AsyncCallback<Boolean> callback);

}
