package com.kaipa.keyword.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.kaipa.keyword.shared.Keyword;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtDogmatix implements EntryPoint {
	private SearchWidget searchWidget = new SearchWidget();
	private AddWidget addWidget = new AddWidget();
	private StatusWidget statusWidget = new StatusWidget();
	private Activity activity;
	
	private  Column<Keyword, String> nameColumn;
	private Column<Keyword, String> addressColumn;
	private Column<Keyword, String> countColumn;
	private Column<Keyword, String> deleteColumn;

	ListDataProvider<Keyword> dataProvider;
	
	CellTable<Keyword> table = new CellTable<Keyword>();

	public void onModuleLoad() {
		activity = new Activity(this);
		RootPanel rootPanel = RootPanel.get();
		setPresenter();
		rootPanel.add(searchWidget);
		rootPanel.add(statusWidget);
		rootPanel.add(addWidget);
		
		// Setup the celltable
		// Create name column.
	    nameColumn = new Column<Keyword, String>(new  ClickableTextCell()){
	        @Override
	        public String getValue(Keyword keyword){
	            return keyword.getKeyword();
	        }

	    };

	    nameColumn.setFieldUpdater(new FieldUpdater<Keyword, String>(){
	        @Override
	        public void update(int index, Keyword keyword, String value){
	        	Window.Location.assign("/" + keyword.getKeyword());
	        }
	    });

	    // Make the name column sortable.
	    nameColumn.setSortable(true);
	    
	    // Create address column.
	    addressColumn = new Column<Keyword, String>(new  ClickableTextCell()){
	        @Override
	        public String getValue(Keyword keyword){
	            return keyword.getUrl();
	        }

	    };

	    addressColumn.setFieldUpdater(new FieldUpdater<Keyword, String>(){
	        @Override
	        public void update(int index, Keyword keyword, String value){
	        	Window.Location.assign("/" + keyword.getKeyword());
	        }
	    });
	    addressColumn.setSortable(true);

	    // Create count column.
	    countColumn = new TextColumn<Keyword>() {
	    	@Override
	    	public String getValue(Keyword keyword) {
	    		return String.valueOf(keyword.getCount());
	    	}
	    };
	    countColumn.setSortable(true);
	    
	    ButtonCell deleteButton = new ButtonCell();
	    deleteColumn = new Column<Keyword, String>(deleteButton){
			@Override
			public String getValue(Keyword object) {
				return "X";
			}
	    };
	    
	    deleteColumn.setFieldUpdater(new FieldUpdater<Keyword, String>() {
	    	  @Override
	    	  public void update(int index, Keyword keyword, String value) {
	    	    activity.delete(keyword.getKeyword());
	    	  }
	    	});
	    
	    // Add the columns.
	    table.addColumn(nameColumn, "Key");
	    table.addColumn(addressColumn, "Url");
	    table.addColumn(countColumn, "Count");
	    table.addColumn(deleteColumn, "Delete");
	    table.setVisibleRange(0, 100);
	    activity.getKeywords();
	    rootPanel.add(table);
	}
	
	private void setPresenter() {
		searchWidget.setPresenter(activity);
		addWidget.setPresenter(activity);
	}

	public void clearAddWidget() {
		// TODO Auto-generated method stub
		addWidget.clear();
	}

	public void renderKeywords(List<Keyword> keywords) {
		// Create a data provider.
	    dataProvider = new ListDataProvider<Keyword>();
	    
	    List<Keyword> actualList = dataProvider.getList();
	    for (Keyword keyword : keywords) {
	    	actualList.add(keyword);
	    }
		ListHandler<Keyword> sortHandler = new ListHandler<Keyword>(actualList);
	    sortHandler.setComparator(countColumn, new Comparator<Keyword>() {
			
			@Override
			public int compare(Keyword arg0, Keyword arg1) {
				return (int) (arg0.getCount() - arg1.getCount());
			}
		});
	    sortHandler.setComparator(nameColumn, new Comparator<Keyword>() {
			@Override
			public int compare(Keyword o1, Keyword o2) {
				return o1.getKeyword().compareTo(o2.getKeyword());
			}
	    	
	    });
	    sortHandler.setComparator(addressColumn, new Comparator<Keyword>() {
	    	@Override
	    	public int compare(Keyword o1, Keyword o2) {
	    		return o1.getUrl().compareTo(o2.getUrl());
	    	}
	    });
	    
	    table.addColumnSortHandler(sortHandler);
	    table.getColumnSortList().push(nameColumn);
	    
	    // Connect the table to the data provider.
	    dataProvider.addDataDisplay(table);
	}

	public void addKeyword(Keyword keyword) {
		dataProvider.getList().add(keyword);
		table.redraw();
	}
	
	public void setError(String error) {
		statusWidget.setError(error);
	}
	
	public void setSuccess(String success) {
		statusWidget.setSuccess(success);
	}

	public void setKey(String key) {
		addWidget.setKey(key);
	}

	public void removeKeyword(String key) {
		List<Keyword> keywords = dataProvider.getList();
		for (Keyword keyword : keywords) {
			if (keyword.getKeyword().equals(key)) {
				keywords.remove(keyword);
				break;
			}
		}
		table.redraw();
	}
}
