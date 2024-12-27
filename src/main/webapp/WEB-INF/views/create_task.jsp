<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Task</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1>Create New Task</h1>

  <form method="post">
    <input type="hidden" name="projectId" value="${projectId}">

    <div class="mb-3">
      <label for="taskTitle" class="form-label">Task Title</label>
      <input type="text" class="form-control" id="taskTitle" name="name" required>
    </div>

    <div class="mb-3">
      <label for="taskDescription" class="form-label">Task Description</label>
      <textarea class="form-control" id="taskDescription" name="description" rows="3" required></textarea>
    </div>

    <div class="mb-3">
      <label for="taskStartDate" class="form-label">Start Date</label>
      <input type="date" class="form-control" id="taskStartDate" name="startDate" required>
    </div>

    <button type="submit" class="btn btn-primary">Create Task</button>
    <a href="${pageContext.request.contextPath}/project" class="btn btn-secondary">Back to Project</a>
  </form>
</div>
</body>
</html>
