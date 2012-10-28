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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AddWidget extends Composite {
	private static AddWidgetUiBinder uiBinder = GWT
			.create(AddWidgetUiBinder.class);

	interface AddWidgetUiBinder extends UiBinder<Widget, AddWidget> {
	}

	public interface Presenter {
		public void add(String key, String url);
	}

	@UiField
	TextBox keyBox;
	@UiField
	TextBox urlBox;
	@UiField
	Button addButton;

	private Presenter presenter;

	public AddWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		urlBox.setText("http://");
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("addButton")
	public void handleClick(ClickEvent event) {
		presenter.add(keyBox.getText(), urlBox.getText());
	}

	@UiHandler("urlBox")
	public void handleEnter(KeyUpEvent event) {
		addButton.setEnabled(shouldEnableAddButton());
		if (addButton.isEnabled()
				&& event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			presenter.add(keyBox.getText(), urlBox.getText());
		}
	}

	@UiHandler("keyBox")
	public void handleEnable(KeyUpEvent event) {
		addButton.setEnabled(shouldEnableAddButton());
	}
	
	public void clear() {
		keyBox.setText("");
		urlBox.setText("http://");
		addButton.setEnabled(shouldEnableAddButton());
		keyBox.setFocus(true);
	}

	private boolean shouldEnableAddButton() {
		return keyBox.getText().trim().length() > 0
				&& urlBox.getText().trim().lastIndexOf("://") != urlBox.getText().trim().length();
	}

	public void setKey(String key) {
		keyBox.setText(key);
		urlBox.setFocus(true);
	}
}
