package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RequestCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LeaveDao dao = new LeaveDao();
            req.setAttribute("types", dao.listLeaveTypes());
            req.getRequestDispatcher("/WEB-INF/jsp/request_create.jsp").forward(req, resp);
        } catch (Exception ex) { throw new ServletException(ex); }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        int typeId = Integer.parseInt(req.getParameter("leaveTypeId"));
        String reason = req.getParameter("reason");
        String ro = req.getParameter("reasonOptionId");
        Integer reasonOptionId = (ro == null || ro.isEmpty()) ? null : Integer.valueOf(ro);
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date from = df.parse(req.getParameter("fromDate"));
            Date to = df.parse(req.getParameter("toDate"));
            LeaveDao dao = new LeaveDao();
            int requestId = dao.createRequest(u.getUserId(), typeId, reasonOptionId, from, to, reason);
            req.getSession().setAttribute("flash", "Tạo đơn thành công: ID=" + requestId);
            resp.sendRedirect(req.getContextPath() + "/request/list");
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
