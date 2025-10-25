package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import com.company.leavemgmt.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class RequestReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        int requestId = Integer.parseInt(req.getParameter("requestId"));
        String action = req.getParameter("action");
        String note = req.getParameter("note");
        boolean approve = "approve".equalsIgnoreCase(action);
        try {
            LeaveDao dao = new LeaveDao();
            dao.review(u.getUserId(), requestId, approve, note);
            req.getSession().setAttribute("flash", (approve ? "Đã duyệt" : "Đã từ chối") + " đơn #" + requestId);
            resp.sendRedirect(req.getContextPath() + "/manager/requests");
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
