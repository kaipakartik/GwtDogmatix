package com.kaipa.keyword.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Activity implements SearchWidget.Presenter, AddWidget.Presenter {
	private final AddServiceAsync addService = GWT.create(AddService.class);
	private final GetKeywordsServiceAsync keywordsService = GWT
			.create(GetKeywordsService.class);
	private final DeleteServiceAsync deleteService = GWT
			.create(DeleteService.class);

	@Override
	public void goToLink(final String key) {
		keywordsService.doesKeyExistGlobally(key, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("No such keyword exists. Please add it");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Window.Location.assign("/" + key);
				}
				// TODO(kaipa): If the result is a failure then show an error
				// message.
			}
		});

	}

	@Override
	public void add(String key, String url) {
		addService.add(key, url, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				Window.alert("failed add");
			}

			public void onSuccess(String result) {
			}
		});

	}

}
