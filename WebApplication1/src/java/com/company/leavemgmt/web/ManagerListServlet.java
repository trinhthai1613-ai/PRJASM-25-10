package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date from, to;
            String f = req.getParameter("fromDate"), t = req.getParameter("toDate");
            if (f == null || t == null || f.isEmpty() || t.isEmpty()) {
                Calendar c = Calendar.getInstance(); c.set(Calendar.DAY_OF_MONTH, 1);
                from = c.getTime(); c.add(Calendar.MONTH, 1); c.add(Calendar.DAY_OF_MONTH, -1); to = c.getTime();
            } else { from = df.parse(f); to = df.parse(t); }
            LeaveDao dao = new LeaveDao();
            req.setAttribute("list", dao.listTreeRequests(u.getUserId(), from, to));
            req.getRequestDispatcher("/WEB-INF/jsp/manager_list.jsp").forward(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
