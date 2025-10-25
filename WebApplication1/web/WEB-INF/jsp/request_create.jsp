<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="includes/header.jspf"/>
<h2>Tạo đơn nghỉ phép</h2>
<form method="post">
  <label>Loại phép:
    <select id="leaveTypeId" name="leaveTypeId" required onchange="loadReasonOptions(this.value)">
      <option value="">--Chọn--</option>
      <% java.util.List types = (java.util.List) request.getAttribute("types");
         for (Object o: types) { java.util.Map m = (java.util.Map)o; %>
        <option value="<%= m.get("LeaveTypeID") %>"><%= m.get("TypeCode") %> - <%= m.get("TypeName") %></option>
      <% } %>
    </select>
  </label><br>
  <label>Lý do chọn sẵn:
    <select id="reasonOptionId" name="reasonOptionId"><option value="">--Không chọn--</option></select>
  </label><br>
  <label>Từ ngày: <input type="date" name="fromDate" required></label><br>
  <label>Đến ngày: <input type="date" name="toDate" required></label><br>
  <label>Ghi chú thêm: <br><textarea name="reason" rows="3" cols="50"></textarea></label><br>
  <button type="submit">Gửi đơn</button>
</form>
<script>
async function loadReasonOptions(typeId){
  if(!typeId){ document.getElementById('reasonOptionId').innerHTML='<option value=\"\">--Không chọn--</option>'; return; }
  const res = await fetch('${pageContext.request.contextPath}/request/reason-options?typeId=' + typeId);
  const data = await res.json();
  const sel = document.getElementById('reasonOptionId');
  sel.innerHTML = '<option value=\"\">--Không chọn--</option>';
  data.forEach(function(it){
    const op = document.createElement('option');
    op.value = it.ReasonOptionID;
    op.text = it.ReasonCode + ' - ' + it.ReasonName;
    sel.appendChild(op);
  });
}
</script>
