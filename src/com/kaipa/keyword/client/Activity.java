package com.kaipa.keyword.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kaipa.keyword.shared.Keyword;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Activity implements SearchWidget.Presenter, AddWidget.Presenter {
	private static final String SERVER_ERROR = "Something went wrong. Check your internet connection and try again";
	
	private final AddServiceAsync addService = GWT.create(AddService.class);
	private final GetKeywordsServiceAsync keywordsService = GWT
			.create(GetKeywordsService.class);
	private final DeleteServiceAsync deleteService = GWT
			.create(DeleteService.class);
	
	private final GwtDogmatix view;
	
	private Set<String> keys;
	
	public Activity(GwtDogmatix view) {
		this.view = view;
	}

	@Override
	public void goToLink(final String key) {
		keywordsService.doesKeyExistGlobally(key, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				view.setError(SERVER_ERROR);
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Window.Location.assign("/" + key);
				} else {
					view.setError(("No such keyword exists. Please add it"));
					view.setKey(key);
				}
			}
		});

	}

	@Override
	public void add(final String key, final String url) {
		if (!addKeyword(key)) {
			view.setError("Key " + key +" already exists");
			return;
		}
		addService.add(key, url, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				view.setError(SERVER_ERROR);
			}
			public void onSuccess(String result) {
				view.clearAddWidget();
				Keyword keyword = new Keyword(key, url);
				view.addKeyword(keyword);
				view.setSuccess("Added " + key +"-> " + url + " successfully");
			}
		});
	}
	
	public void getKeywords() {
		
		keywordsService.getKeywords(new AsyncCallback<List<Keyword>>() {
			@Override
			public void onFailure(Throwable caught) {
				view.setError(SERVER_ERROR);
			}

			@Override
			public void onSuccess(List<Keyword> result) {
				setKeywords(result);
				view.renderKeywords(result);
			}
		});
	}

	public void setKeywords(List<Keyword> keywords) {
		keys = new HashSet<String>();
		for (Keyword keyword : keywords) {
			keys.add(keyword.getKeyword());
		}
	}
	
	public boolean addKeyword(String key) {
		return keys.add(key);
	}
	
	public boolean removeKeyword(String key) {
		return keys.remove(key);
	}

	public void delete(final String key) {
		deleteService.delete(key, "", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				view.setError(SERVER_ERROR);
			}

			@Override
			public void onSuccess(String result) {
				view.setSuccess("Successfully deleted");
				removeKeyword(key);
				view.removeKeyword(key);
			}
		});
	}
}
