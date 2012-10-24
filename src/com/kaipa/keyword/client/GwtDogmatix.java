package com.kaipa.keyword.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtDogmatix implements EntryPoint {
	private SearchWidget searchWidget = new SearchWidget();
	private AddWidget addWidget = new AddWidget();
	private Activity activity;

	public void onModuleLoad() {
		activity = new Activity(this);
		RootPanel rootPanel = RootPanel.get();
		setPresenter();
		rootPanel.add(searchWidget);
		rootPanel.add(addWidget);
	}
	
	private void setPresenter() {
		searchWidget.setPresenter(activity);
		addWidget.setPresenter(activity);
	}

	public void clearAddWidget() {
		// TODO Auto-generated method stub
		addWidget.clear();
	}
}
