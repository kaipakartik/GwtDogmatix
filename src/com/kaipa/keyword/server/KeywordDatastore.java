package com.kaipa.keyword.server;

import com.google.appengine.api.NamespaceManager;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.kaipa.keyword.shared.Keyword;

public class KeywordDatastore {
	public static Keyword findForUser(String key) {
		Objectify service = getService();
		try {
			NamespaceManager.set(LoggedInUser.getUserId());
			Keyword keyword = service.get(Keyword.class, key);
			NamespaceManager.set("");
			return keyword;
		} catch (NotFoundException e) {
			NamespaceManager.set("");
			return null;
		}
	}
	
	public static Keyword findInGlobalAsWell(String key) {
		Objectify service = getService();
		try {
			NamespaceManager.set(LoggedInUser.getUserId());
			Keyword keyword = service.get(Keyword.class, key);
			NamespaceManager.set("");
			return keyword;
		} catch (NotFoundException e) {
			NamespaceManager.set("");
			try {
				return service.get(Keyword.class, key);
			} catch(NotFoundException f) {
				return null;
			}
		}
	}

	public static void save(Keyword keyword) {
		NamespaceManager.set(LoggedInUser.getUserId());
		getService().put(keyword);
		NamespaceManager.set("");
	}
	
	public static void update(Keyword keyword) {
		if (findForUser(keyword.getKeyword()) != null) {
			NamespaceManager.set(LoggedInUser.getUserId());
			getService().put(keyword);
		} else {
			NamespaceManager.set("");
			getService().put(keyword);
		}
		NamespaceManager.set("");
	}

	public static void delete(String key) {
		NamespaceManager.set(LoggedInUser.getUserId());
		getService().delete(Keyword.class, key);
		NamespaceManager.set("");
	}

	private static Objectify getService() {
		return ObjectifyService.begin();
	}

}
