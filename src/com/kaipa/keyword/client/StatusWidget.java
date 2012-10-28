package com.kaipa.keyword.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StatusWidget extends Composite {
	interface StatusStyle extends CssResource {
	    String error();
	    String success();
	  }
	private static StatusWidgetUiBinder uiBinder = GWT
			.create(StatusWidgetUiBinder.class);

	interface StatusWidgetUiBinder extends UiBinder<Widget, StatusWidget> {
	}

	@UiField Label status;
	 @UiField StatusStyle style;
	
	public StatusWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setError(String error) {
		status.setText(error);
		status.setStyleName(style.error());
	}
	
	public void setSuccess(String success) {
		status.setText(success);
		status.setStyleName(style.success());
	}
	
	private void clear() {
		status.setText("");
	}
}
