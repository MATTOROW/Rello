<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Subtask</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1>Create Subtask</h1>

  <form method="post">

    <div class="mb-3">
      <label for="subtaskName" class="form-label">Subtask Title</label>
      <input type="text" class="form-control" id="subtaskName" name="name" required>
    </div>

    <div class="mb-3">
      <label for="subtaskDescription" class="form-label">Subtask Description</label>
      <textarea class="form-control" id="subtaskDescription" name="description" rows="3" required></textarea>
    </div>

    <button type="submit" class="btn btn-primary">Create Subtask</button>
    <a href="${pageContext.request.contextPath}/task" class="btn btn-secondary">Back to task</a>
  </form>
</div>
</body>
</html>
