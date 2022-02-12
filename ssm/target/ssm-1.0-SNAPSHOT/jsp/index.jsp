<%--
  Created by IntelliJ IDEA.
  User: 李非凡
  Date: 2021/12/24
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <style type="text/css">
        #login{
            width: 400px;
            height: 400px;
            margin: 100px auto;
        }
    </style>
</head>
<body>
<div id="login">
    <h1>登录</h1>
    <form action="/login" method="post">
        账号：  <input type="text" name="id"/> <br/>
        密码：  <input type="password" name="password"/> <br/><br/>
        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <input type="submit"  value="登录"/>
        &nbsp&nbsp&nbsp
        <input type="submit"  value="注册" formaction="/signup" />
    </form>
</div>
</body>
</html>

<script>
    var ws = new WebSocket('ws://localhost:8080/webSocketServer')
    ws.onopen = () => {
        console.log('ws 连接成功！')
    }
</script>
