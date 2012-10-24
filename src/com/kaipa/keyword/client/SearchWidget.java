package com.kaipa.keyword.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SearchWidget extends Composite {
	private static SearchWidgetUiBinder uiBinder = GWT
			.create(SearchWidgetUiBinder.class);

	interface SearchWidgetUiBinder extends UiBinder<Widget, SearchWidget> {
	}
	public interface Presenter {
		public void goToLink(String key);
	}

	@UiField
	TextBox goBox;
	@UiField
	Button goButton;

	private Presenter presenter;

	public SearchWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("goButton")
	public void handleClick(ClickEvent event) {
		presenter.goToLink(goBox.getText());
	}

	@UiHandler("goBox")
	public void handleEnter(KeyUpEvent event) {
		goButton.setEnabled(goBox.getText().trim().length() > 0);
		if (goButton.isEnabled()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			presenter.goToLink(goBox.getText());
		}
	}
}
