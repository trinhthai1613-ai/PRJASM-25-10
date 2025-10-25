<%@ page contentType="text/html;charset=UTF-8" %>
<html><head><title>Đăng nhập</title></head><body>
  <h2>Đăng nhập</h2>
  <form method="post">
    <label>Username: <input type="text" name="username" required></label><br>
    <label>Password: <input type="password" name="password" required></label><br>
    <button type="submit">Login</button>
  </form>
  <div style="color:red;">${requestScope.error}</div>
  <p style="color:#666;">* Demo: cấu hình <code>security.demo.ignorePassword=true</code> trong <code>app.properties</code>.</p>
</body></html>
