<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Subtask Details</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">${subtask.getName()}</h1>
  <p>${subtask.getDescription()}</p>

  <h4>Status: ${subtask.isCompleted()}</h4>
  <p>Created on: ${subtask.getEndDate()}</p>

  <a href="${pageContext.request.contextPath}/task" class="btn btn-secondary">Back to Task</a>
</div>
</body>
</html>
