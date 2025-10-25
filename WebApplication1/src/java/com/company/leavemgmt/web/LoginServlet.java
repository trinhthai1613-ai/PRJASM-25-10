package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.UserDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            UserDao dao = new UserDao();
            User user = dao.login(username, password);
            if (user == null) {
                req.setAttribute("error", "Sai thông tin đăng nhập.");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
