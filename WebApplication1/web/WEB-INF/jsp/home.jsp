<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h3>Xin chào, ${sessionScope.user.fullName} (${sessionScope.user.username}) - Phòng: ${sessionScope.user.divisionName}</h3>
<ul>
  <li><a href="${pageContext.request.contextPath}/request/create">Tạo đơn nghỉ phép</a></li>
  <li><a href="${pageContext.request.contextPath}/request/list">Xem các đơn của tôi</a></li>
  <li><a href="${pageContext.request.contextPath}/manager/requests">Duyệt đơn cấp dưới (nếu là TL/TP)</a></li>
  <li><a href="${pageContext.request.contextPath}/agenda">Agenda phòng trong khoảng ngày</a></li>
  <li><a href="${pageContext.request.contextPath}/balance">Số dư phép năm hiện tại</a></li>
</ul>
<div style="color:green;">${sessionScope.flash}</div>
<% session.removeAttribute("flash"); %>
