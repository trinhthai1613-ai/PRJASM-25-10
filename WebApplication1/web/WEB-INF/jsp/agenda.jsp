<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h2>Agenda phòng</h2>
<form method="post">
  Phòng:
  <select name="divisionId" required>
    <option value="">--Chọn phòng--</option>
    <% java.util.List divs = (java.util.List) request.getAttribute("divisions");
       for (Object o: divs) { java.util.Map m = (java.util.Map)o;
          String sel = (request.getAttribute("divisionId")!=null && (Integer)request.getAttribute("divisionId")==((Integer)m.get("DivisionID"))) ? "selected" : "";
    %>
      <option value="<%= m.get("DivisionID") %>" <%= sel %>><%= m.get("DivisionName") %></option>
    <% } %>
  </select>
  Từ: <input type="date" name="fromDate" value="${requestScope.fromDate}" required>
  Đến: <input type="date" name="toDate" value="${requestScope.toDate}" required>
  <button type="submit">Xem</button>
</form>
<%
  java.util.List rows = (java.util.List) request.getAttribute("rows");
  if (rows != null) {
%>
<table border="1" cellpadding="4" cellspacing="0">
  <tr><th>Nhân sự</th><th>Ngày</th><th>Tình trạng</th></tr>
  <% for (Object o: rows) { java.util.Map m = (java.util.Map)o; %>
    <tr>
      <td><%= m.get("FullName") %></td>
      <td><%= m.get("WorkingDate") %></td>
      <td><%= m.get("Attendance") %></td>
    </tr>
  <% } %>
</table>
<% } %>
