<%--
  Created by IntelliJ IDEA.
  User: 李非凡
  Date: 2021/12/26
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <style type="text/css">
        #register{
            width: 400px;
            height: 400px;
            margin: 100px auto;
        }
    </style>
</head>
<body>
    <div id="register">
        <h1>注册</h1>
        <form action="/register">
            账号：  <input type="text" name="id"/> <br/>
            昵称：  <input type="text" name="username"/> <br/>
            密码：  <input type="password" name="password"/> <br/><br/>
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <input type="submit"  value="注册" />
        </form>
    </div>
</body>
</html>
