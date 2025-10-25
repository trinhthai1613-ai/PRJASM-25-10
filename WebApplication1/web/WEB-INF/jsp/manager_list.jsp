<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h2>Đơn cần duyệt của cấp dưới</h2>
<form method="get">
  Từ ngày: <input type="date" name="fromDate" value="${param.fromDate}">
  Đến ngày: <input type="date" name="toDate" value="${param.toDate}">
  <button type="submit">Lọc</button>
</form>
<table border="1" cellpadding="4" cellspacing="0">
  <tr><th>ID</th><th>Mã</th><th>Người tạo</th><th>Phòng</th><th>Loại</th><th>Từ</th><th>Đến</th><th>Ngày làm</th><th>Trạng thái</th><th>Hành động</th></tr>
  <% java.util.List list = (java.util.List) request.getAttribute("list");
     if (list != null) for (Object o: list) { java.util.Map m = (java.util.Map)o; %>
    <tr>
      <td><%= m.get("RequestID") %></td>
      <td><%= m.get("RequestCode") %></td>
      <td><%= m.get("CreatedBy") %></td>
      <td><%= m.get("DivisionName") %></td>
      <td><%= m.get("TypeName") %></td>
      <td><%= m.get("FromDate") %></td>
      <td><%= m.get("ToDate") %></td>
      <td><%= m.get("DaysBusiness") %></td>
      <td><%= m.get("CurrentStatus") %></td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/request/review" style="display:inline;">
          <input type="hidden" name="requestId" value="<%= m.get("RequestID") %>">
          <input type="hidden" name="action" value="approve">
          <input type="text" name="note" placeholder="Ghi chú"/>
          <button type="submit">Approve</button>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/request/review" style="display:inline;">
          <input type="hidden" name="requestId" value="<%= m.get("RequestID") %>">
          <input type="hidden" name="action" value="reject">
          <input type="text" name="note" placeholder="Ghi chú"/>
          <button type="submit">Reject</button>
        </form>
      </td>
    </tr>
  <% } %>
</table>
