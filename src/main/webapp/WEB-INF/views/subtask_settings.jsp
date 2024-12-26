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
    <input type="hidden" name="subtaskId" value="${subtask.subtaskId()}">
    <div class="mb-4">
      <label for="subtaskName" class="form-label">Project Name</label>
      <input type="text" class="form-control" id="subtaskName" name="name" value="${subtask.name()}" required>
    </div>
    <div class="mb-4">
      <label for="subtaskDescription" class="form-label">Project Description</label>
      <textarea class="form-control" id="subtaskDescription" name="description" required>${subtask.description()}</textarea>
    </div>
    <button type="submit" class="btn btn-primary">Save Changes</button>
  </form>
  <form method="post">
    <input type="hidden" id="subtaskId" name="subtaskId" value="${subtask.subtaskId()}">
    <button type="submit" name="deleteSubtask" class="btn btn-danger mt-4">Delete Subtask</button>
  </form>
</div>
</body>
</html>
