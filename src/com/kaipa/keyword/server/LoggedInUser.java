package com.kaipa.keyword.server;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoggedInUser {
	private static final UserService userService = UserServiceFactory.getUserService();

	public static String getUserId() {
		return userService.getCurrentUser().getUserId();
	}
}
