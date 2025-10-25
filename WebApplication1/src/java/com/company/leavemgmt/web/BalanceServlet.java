package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class BalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String y = req.getParameter("year"); if (y != null && !y.isEmpty()) year = Integer.parseInt(y);
        try {
            LeaveDao dao = new LeaveDao();
            req.setAttribute("balances", dao.balances(u.getUserId(), year));
            req.setAttribute("year", year);
            req.getRequestDispatcher("/WEB-INF/jsp/balance.jsp").forward(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
