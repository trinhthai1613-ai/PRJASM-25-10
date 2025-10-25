<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h2>Số dư phép năm ${requestScope.year}</h2>
<form method="get">
  Năm: <input type="number" name="year" value="${requestScope.year}" min="2000" max="2100">
  <button type="submit">Xem</button>
</form>
<table border="1" cellpadding="4" cellspacing="0">
  <tr><th>Loại</th><th>Tên</th><th>Năm</th><th>CarryOver</th><th>Đã dùng</th><th>Còn lại</th><th>Còn lại + Carry</th></tr>
  <% java.util.List balances = (java.util.List) request.getAttribute("balances");
     for (Object o: balances) { java.util.Map m = (java.util.Map)o; %>
    <tr>
      <td><%= m.get("TypeCode") %></td>
      <td><%= m.get("TypeName") %></td>
      <td><%= m.get("Year") %></td>
      <td><%= m.get("CarryOver") %></td>
      <td><%= m.get("UsedDays") %></td>
      <td><%= m.get("BalanceDays") %></td>
      <td><%= m.get("RemainingIncludingCarry") %></td>
    </tr>
  <% } %>
</table>
