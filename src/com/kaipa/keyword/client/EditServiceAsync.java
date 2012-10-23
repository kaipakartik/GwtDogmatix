package com.kaipa.keyword.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EditServiceAsync {

	void edit(String key, String url, AsyncCallback<String> callback);

}
