<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Project Settings</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h1>Task Settings</h1>

  <!-- Success message -->
  <c:if test="${not empty message}">
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
  </c:if>

  <!-- Форма для редактирования названия и описания проекта -->
  <form method="post">
    <input type="hidden" name="taskId" value="${task.taskId()}">
    <div class="mb-4">
      <label for="taskName" class="form-label">Project Name</label>
      <input type="text" class="form-control" id="taskName" name="name" value="${task.name()}" required>
    </div>
    <div class="mb-4">
      <label for="taskDescription" class="form-label">Project Description</label>
      <textarea class="form-control" id="taskDescription" name="description" required>${task.description()}</textarea>
    </div>
    <div class="mb-3">
      <label for="startDate" class="form-label">Start Date</label>
      <input type="date" class="form-control" id="startDate" name="startDate" value="${task.startDate()}" required>
    </div>
    <button type="submit" class="btn btn-primary">Save Changes</button>
  </form>
  <form method="post">
    <input type="hidden" id="taskId" name="taskId" value="${task.taskId()}">
    <button type="submit" name="deleteTask" class="btn btn-danger mt-4">Delete Task</button>
  </form>
</div>
</body>
</html>
