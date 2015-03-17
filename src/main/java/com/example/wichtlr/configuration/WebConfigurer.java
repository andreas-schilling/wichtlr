package com.example.wichtlr.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigurer implements ServletContextInitializer {
	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		initH2Console(servletContext);
	}

	/**
	 * Initializes the H2 console.
	 *
	 * @param servletContext
	 *            the servlet context of the application
	 */
	private void initH2Console(final ServletContext servletContext) {
		log.debug("Initialize H2 console");
		final org.h2.server.web.WebServlet h2Servlet = new org.h2.server.web.WebServlet();
		final ServletRegistration.Dynamic h2ConsoleServlet = servletContext
				.addServlet("H2Console", h2Servlet);
		h2ConsoleServlet.addMapping("/dbconsole/*");
		h2ConsoleServlet.setLoadOnStartup(1);
		h2ConsoleServlet.setInitParameter("webAllowOthers",
				Boolean.TRUE.toString());
	}
}
