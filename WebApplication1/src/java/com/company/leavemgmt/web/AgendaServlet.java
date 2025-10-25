package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AgendaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserDao udao = new UserDao();
            req.setAttribute("divisions", udao.listDivisions());
            req.getRequestDispatcher("/WEB-INF/jsp/agenda.jsp").forward(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int divisionId = Integer.parseInt(req.getParameter("divisionId"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date from = df.parse(req.getParameter("fromDate"));
            Date to   = df.parse(req.getParameter("toDate"));
            LeaveDao dao = new LeaveDao();
            req.setAttribute("rows", dao.agenda(divisionId, from, to));
            req.setAttribute("divisionId", divisionId);
            req.setAttribute("fromDate", req.getParameter("fromDate"));
            req.setAttribute("toDate", req.getParameter("toDate"));
            doGet(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
