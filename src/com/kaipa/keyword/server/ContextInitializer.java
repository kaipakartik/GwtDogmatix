package com.kaipa.keyword.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.kaipa.keyword.shared.Keyword;

public class ContextInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ObjectifyService.register(Keyword.class);
	}
}
