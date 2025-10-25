package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class RequestListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        try {
            LeaveDao dao = new LeaveDao();
            req.setAttribute("my", dao.listMyRequests(u.getUserId()));
            req.getRequestDispatcher("/WEB-INF/jsp/request_list.jsp").forward(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
