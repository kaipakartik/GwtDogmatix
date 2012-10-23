package com.kaipa.keyword.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kaipa.keyword.shared.Keyword;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtDogmatix implements EntryPoint {
	private VerticalPanel mainPanel;
	private HorizontalPanel addPanel;
	
	private FlexTable privateKeywordsTable;
	private FlexTable globalKeywordsTable;
	
	
	private Label keywordLabel = new Label("Key");
	private Label urlLabel = new Label("Url");
	private TextBox keywordBox;
	private TextBox urlBox;
	
	private Button addButton;
	
	private List<String> keywords = new ArrayList<String>(); ;
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final AddServiceAsync addService = GWT.create(AddService.class);
	private final GetKeywordsServiceAsync keywordsService = GWT.create(GetKeywordsService.class);
	private final DeleteServiceAsync deleteService = GWT.create(DeleteService.class);
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		mainPanel = new VerticalPanel();
		addPanel = new HorizontalPanel();
		
		
		// Create the horizontal Panel
		keywordBox = new TextBox();
		urlBox = new TextBox();
		urlBox.setText("http://");

		addButton = new Button("Add");
		
		addPanel.add(keywordLabel);
		addPanel.add(keywordBox);
		addPanel.add(urlLabel);
		addPanel.add(urlBox);
		addPanel.add(addButton);
		
		privateKeywordsTable = new FlexTable();
		privateKeywordsTable.setText(0, 0, "Key");
		privateKeywordsTable.setText(0, 1, "Url");
		privateKeywordsTable.setText(0, 2, "Edit");
		privateKeywordsTable.setText(0, 3, "Remove");
		
		keywordsService.getKeywords(new AsyncCallback<List<Keyword>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failure");
			}

			@Override
			public void onSuccess(List<Keyword> result) {
				// TODO Auto-generated method stub
				keywordBox.setFocus(true);
				for (Keyword keyword : result) {
					addKeyAndUrl(keyword.getKeyword(), keyword.getUrl());
				}
			}
			
		});
		
		mainPanel.add(addPanel);
		mainPanel.add(privateKeywordsTable);
		rootPanel.add(mainPanel);
		
		
		// Add the event handlers
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addKeyAndUrl();
			}
		});
	}

	protected void addKeyAndUrl() {
		keywordBox.setFocus(true);
		addKeyAndUrl(keywordBox.getText(), urlBox.getText());
	}
	
	protected void addKeyAndUrl(final String keyword, final String url) {
		int rows = privateKeywordsTable.getRowCount();
		keywords.add(keyword);
		privateKeywordsTable.setText(rows, 0, keyword);
		privateKeywordsTable.setText(rows, 1, url);
		keywordBox.setText("");
		urlBox.setText("http://");
		addService.add(keyword, url, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				Window.alert("failed add");
			}
			public void onSuccess(String result) {
			}
		});
		
		// Make a servlet call here to add the keyword
		Button removeStock = new Button("x");
		privateKeywordsTable.setWidget(rows, 3, removeStock);
		removeStock.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {					
		        deleteService.delete(keyword, url, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Delete failed");
					}
					@Override
					public void onSuccess(String result) {
						
					}
		        });
		    	int removedIndex = keywords.indexOf(keyword);
		        keywords.remove(removedIndex);
		        privateKeywordsTable.removeRow(removedIndex + 1);
		    }
		    });

	}

//	/**
//	 * This is the entry point method.
//	 */
//	public void onModuleLoad() {
//		final Button sendButton = new Button("Send");
//		final TextBox nameField = new TextBox();
//		nameField.setText("GWT User");
//		final Label errorLabel = new Label();
//
//		// We can add style names to widgets
//		sendButton.addStyleName("sendButton");
//
//		// Add the nameField and sendButton to the RootPanel
//		// Use RootPanel.get() to get the entire body element
//		RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
//		RootPanel.get("errorLabelContainer").add(errorLabel);
//
//		// Focus the cursor on the name field when the app loads
//		nameField.setFocus(true);
//		nameField.selectAll();
//
//		// Create the popup dialog box
//		final DialogBox dialogBox = new DialogBox();
//		dialogBox.setText("Remote Procedure Call");
//		dialogBox.setAnimationEnabled(true);
//		final Button closeButton = new Button("Close");
//		// We can set the id of a widget by accessing its Element
//		closeButton.getElement().setId("closeButton");
//		final Label textToServerLabel = new Label();
//		final HTML serverResponseLabel = new HTML();
//		VerticalPanel dialogVPanel = new VerticalPanel();
//		dialogVPanel.addStyleName("dialogVPanel");
//		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
//		dialogVPanel.add(textToServerLabel);
//		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
//		dialogVPanel.add(serverResponseLabel);
//		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//		dialogVPanel.add(closeButton);
//		dialogBox.setWidget(dialogVPanel);
//
//		// Add a handler to close the DialogBox
//		closeButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				dialogBox.hide();
//				sendButton.setEnabled(true);
//				sendButton.setFocus(true);
//			}
//		});
//
//		// Create a handler for the sendButton and nameField
//		class MyHandler implements ClickHandler, KeyUpHandler {
//			/**
//			 * Fired when the user clicks on the sendButton.
//			 */
//			public void onClick(ClickEvent event) {
//				sendNameToServer();
//			}
//
//			/**
//			 * Fired when the user types in the nameField.
//			 */
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					sendNameToServer();
//				}
//			}
//
//			/**
//			 * Send the name from the nameField to the server and wait for a response.
//			 */
//			private void sendNameToServer() {
//				// First, we validate the input.
//				errorLabel.setText("");
//				String textToServer = nameField.getText();
//				if (!FieldVerifier.isValidName(textToServer)) {
//					errorLabel.setText("Please enter at least four characters");
//					return;
//				}
//
//				// Then, we send the input to the server.
//				sendButton.setEnabled(false);
//				textToServerLabel.setText(textToServer);
//				serverResponseLabel.setText("");
//				greetingService.greetServer(textToServer,
//						new AsyncCallback<String>() {
//							public void onFailure(Throwable caught) {
//								// Show the RPC error message to the user
//								dialogBox
//										.setText("Remote Procedure Call - Failure");
//								serverResponseLabel
//										.addStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(SERVER_ERROR);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//
//							public void onSuccess(String result) {
//								dialogBox.setText("Remote Procedure Call");
//								serverResponseLabel
//										.removeStyleName("serverResponseLabelError");
//								serverResponseLabel.setHTML(result);
//								dialogBox.center();
//								closeButton.setFocus(true);
//							}
//						});
//			}
//		}
//
//		// Add a handler to send the name to the server
//		MyHandler handler = new MyHandler();
//		sendButton.addClickHandler(handler);
//		nameField.addKeyUpHandler(handler);
//	}
}
