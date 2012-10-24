package com.kaipa.keyword.server;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaipa.keyword.shared.Keyword;

@SuppressWarnings("serial")
public class KeywordServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		process(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		process(req, resp);
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query = req.getParameter("q") == null ? req
				.getParameter("query") : req.getParameter("q");
		if (query == null) {
			resp.sendRedirect("http://www.google.com");
			return;
		}
		query = query.toLowerCase();
		Keyword keyword = KeywordDatastore.findInGlobalAsWell(query);
		if (keyword != null) {
			keyword.incrementCount();
			KeywordDatastore.update(keyword);
			resp.sendRedirect(keyword.getUrl());
		} else {
			resp.sendRedirect(String.format("/?k=%s",
					URLEncoder.encode(query, "UTF-8")));
		}
	}
}
