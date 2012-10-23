package com.kaipa.keyword.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteServiceAsync {

	void delete(String key, String url, AsyncCallback<String> callback);

}
