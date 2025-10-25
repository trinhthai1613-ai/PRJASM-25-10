package com.company.leavemgmt.web;

import com.company.leavemgmt.dao.LeaveDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ReasonOptionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeId = req.getParameter("typeId");
        resp.setContentType("application/json;charset=UTF-8");
        if (typeId == null) { resp.getWriter().write("[]"); return; }
        try (PrintWriter out = resp.getWriter()) {
            LeaveDao dao = new LeaveDao();
            List<Map<String,Object>> list = dao.listReasonOptions(Integer.parseInt(typeId));
            StringBuilder sb = new StringBuilder("[");
            for (int i=0;i<list.size();i++) {
                Map<String,Object> m = list.get(i);
                sb.append("{\"ReasonOptionID\":").append(m.get("ReasonOptionID"))
                  .append(",\"ReasonCode\":\"").append(m.get("ReasonCode")).append("\"")
                  .append(",\"ReasonName\":\"").append(m.get("ReasonName")).append("\"}");
                if (i<list.size()-1) sb.append(",");
            }
            sb.append("]");
            out.write(sb.toString());
        } catch (Exception ex) { throw new ServletException(ex); }
    }
}
