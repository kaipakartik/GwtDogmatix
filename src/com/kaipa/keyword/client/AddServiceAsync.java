package com.kaipa.keyword.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AddServiceAsync {

	void add(String key, String url, AsyncCallback<Boolean> callback);

}
