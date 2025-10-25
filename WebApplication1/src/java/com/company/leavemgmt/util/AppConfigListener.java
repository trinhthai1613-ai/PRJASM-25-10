package com.company.leavemgmt.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String url  = ctx.getInitParameter("db.url");
        String user = ctx.getInitParameter("db.user");
        String pass = ctx.getInitParameter("db.password");
        String ignore = ctx.getInitParameter("security.demo.ignorePassword");
        boolean ignorePw = (ignore == null) ? true : Boolean.parseBoolean(ignore);
        Config.init(url, user, pass, ignorePw);
    }
}
