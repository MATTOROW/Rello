<%--
  Created by IntelliJ IDEA.
  User: pnikita
  Date: 20.12.2024
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Project</title>
</head>
<body>
    <form method="POST">
      <p><input type="text" placeholder="Project name" name="name" maxlength="255">
      <input type="text" placeholder="Project description" name="description"></p>
      <button class="btn btn-primary" type="submit">Create</button>
    </form>
</body>
</html>
