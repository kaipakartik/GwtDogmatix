package com.kaipa.keyword.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SearchWidget extends Composite {
	private static SearchWidgetUiBinder uiBinder = GWT
			.create(SearchWidgetUiBinder.class);

	interface SearchWidgetUiBinder extends UiBinder<Widget, SearchWidget> {
	}
	public interface Presenter {
		public void goToLink(String key);
	}

	private Presenter presenter;

	public SearchWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
