<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h2>Đơn của tôi</h2>
<table border="1" cellpadding="4" cellspacing="0">
  <tr><th>Mã</th><th>Loại</th><th>Lý do</th><th>Từ</th><th>Đến</th><th>Ngày làm</th><th>Trạng thái</th></tr>
  <% java.util.List my = (java.util.List) request.getAttribute("my");
     for (Object o: my) { java.util.Map m = (java.util.Map)o; %>
    <tr>
      <td><%= m.get("RequestCode") %></td>
      <td><%= m.get("TypeCode") %></td>
      <td><%= m.get("ReasonCode") %></td>
      <td><%= m.get("FromDate") %></td>
      <td><%= m.get("ToDate") %></td>
      <td><%= m.get("DaysBusiness") %></td>
      <td><%= m.get("StatusName") %></td>
    </tr>
  <% } %>
</table>
